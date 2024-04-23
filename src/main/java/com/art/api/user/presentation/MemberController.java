package com.art.api.user.presentation;

import com.art.api.common.domain.entity.GenreList;
import com.art.api.core.exception.ClientUserNotFoundException;
import com.art.api.core.response.ApiResponse;
import com.art.api.product.application.ArtService;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.user.application.MemberService;
import com.art.api.user.domain.entity.AuthSocial;
import com.art.api.user.domain.entity.MemberProfile;
import com.art.api.user.domain.entity.User;
import com.art.api.user.domain.entity.UserAuth;
import com.art.api.user.domain.model.LoginResponse;
import com.art.api.user.domain.model.MypageResponse;
import com.art.api.user.domain.model.PreferGenreResponse;
import com.art.api.user.domain.model.UpdateUserInfoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final ArtService artService;

    @GetMapping("/user")
    @Operation(summary = "로그인 정보", description = "로그인 유저정보 가져오기")
    public ApiResponse<?> getUser() {
        User user = securityUserInfo();
        LoginResponse loginResponse = LoginResponse.of(user.getUserId(), user.getUserName());
        return ApiResponse.success("data", loginResponse);
    }


    @GetMapping("/mypage")
    @Operation(summary = "회원정보", description = "아이디/이름/닉네임/프로필이미지/이메일/성별/저장한 연극 수")
    public ApiResponse<?> getMyPageInfo() {
        User clientUser = securityUserInfo();
        MemberProfile profile = memberService.getProfile(clientUser);
        if (profile == null) {
            return ApiResponse.notFoundUser();
        }
        UserAuth userAuth = memberService.getUserInfo(clientUser);
        if (userAuth == null) {
            return ApiResponse.notFoundUser();
        }
        AuthSocial authSocial = memberService.getAuthInfo(clientUser);
        if (authSocial == null) {
            return ApiResponse.notFoundUser();
        }
        MypageResponse response = MypageResponse.of(clientUser.getUserId(), clientUser.getUserName(), profile.getNickNm(), authSocial.getProfileImgUrl(), userAuth.getEmail(), userAuth.getSex(), memberService.countSaveHistByUser(clientUser));
        return ApiResponse.success("data", response);
    }

    @PatchMapping("/user")
    @Operation(summary = "회원정보 재입력", description = "회원 필수 입력 정보 업데이트")
    public ApiResponse<?> updateUserInfo(@RequestBody UpdateUserInfoRequest request) {
        User user = securityUserInfo();
        memberService.updateUserInfo(user, request);
        return ApiResponse.success("data", "회원 정보가 정상적으로 수정되었습니다.");
    }


    @DeleteMapping("/user")
    @Operation(summary = "회원탈퇴", description = "회원 탈퇴하기(무조건 삭제)")
    public ApiResponse<?> userWithDraw() {
        User user = securityUserInfo();
        memberService.deleteUser(user);
        return ApiResponse.success("data", "회원 탈퇴가 정상적으로 처리되었습니다.");
    }


    @PostMapping("/save/{art_id}")
    @Operation(summary = "저장하기 / 저장취소(두번선택)")
    public ApiResponse<?> save(@PathVariable(name = "art_id") @Schema(description = "저장할/저장취소할 공연ID") String artId) {
        User user = securityUserInfo();
        Optional<ArtList> artList = artService.findByArtId(artId);
        if (artList.isEmpty()) {
            return ApiResponse.notExistData();
        }
        if (memberService.updateSaveHist(user, artList.get())) {
            return ApiResponse.success("data", "저장 완료");
        } else {
            return ApiResponse.success("data", "저장취소 완료");
        }

    }

    @GetMapping("/user/mylist")
    @Operation(summary = "저장한 연극 목록")
    public ApiResponse<?> saveList(@PageableDefault(size = 6, sort = "artId", direction = Sort.Direction.DESC) Pageable pagable) {
        User user = securityUserInfo();
        Page<ArtListDTO> artList = artService.convertArtList(memberService.retrieveMySaveList(pagable, user));
        if(artList == null) {
            return ApiResponse.notExistData();
        }
        return ApiResponse.success("data", artList);
    }

    @GetMapping("/user/prefer")
    @Operation(summary = "선호 장르 목록 (map)")
    public ApiResponse<?> preferGenre(){
        User user = securityUserInfo();
        Map<GenreList, Integer> genreListIntegerMap = memberService.retrievePreferGenreCount(user);
        if (genreListIntegerMap == null) {
            return ApiResponse.notExistData();
        }
        PreferGenreResponse response = new PreferGenreResponse();
        response.setTotCount(memberService.countSaveHistByUser(user));
        List<PreferGenreResponse.PreferList> list = new ArrayList<>();
        for (GenreList genreList : genreListIntegerMap.keySet()) {
            PreferGenreResponse.PreferList dto = new PreferGenreResponse.PreferList();
            dto.setSelectCount(genreListIntegerMap.get(genreList));
            dto.setGenreList(genreList);
            list.add(dto);
        }
        response.setContent(list);
        return ApiResponse.success("data", response);
    }

    @GetMapping("/suggest")
    @Operation(summary = "추천 공연 목록")
    public ApiResponse<?> retrieveListSuggestList(){
        User user = securityUserInfo();
        // 유저가 저장한 공연이 세개 이하면 빈 값 반환
        Long count = memberService.countSaveHistByUser(user);
        if (count < 3) {
            return ApiResponse.notEnoughData();
        } else {
            List<ArtListDTO> artList = artService.convertArtList(memberService.retrieveListSuggestList(user));
            if (artList == null) {
                return ApiResponse.notExistData();
            }
            Collections.shuffle(artList);
            return ApiResponse.success("data", artList);
        }
    }


    private User securityUserInfo() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("login info : {}", principal.getUsername());
        Optional<User> clientUserOptional = memberService.getClientUser(principal.getUsername());
        if(clientUserOptional.isEmpty()) {
            throw new ClientUserNotFoundException();
        }
        return clientUserOptional.get();
    }


}
