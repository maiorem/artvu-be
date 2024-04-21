package com.art.api.user.presentation;

import com.art.api.core.exception.ClientUserNotFoundException;
import com.art.api.core.exception.ItemNotFoundException;
import com.art.api.core.exception.NotEnoughDataException;
import com.art.api.core.exception.UserInfoNotExistException;
import com.art.api.core.response.ApiResponse;
import com.art.api.discover.domain.dto.DiscoveryDTO;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @Operation(summary = "마이페이지", description = "마이페이지 유저정보 가져오기")
    public ApiResponse<?> getMyPageInfo() {
        User clientUser = securityUserInfo();
        MemberProfile profile = memberService.getProfile(clientUser);
        if (profile == null) {
            throw new UserInfoNotExistException();
        }
        UserAuth userAuth = memberService.getUserInfo(clientUser);
        if (userAuth == null) {
            throw new UserInfoNotExistException();
        }
        AuthSocial authSocial = memberService.getAuthInfo(clientUser);
        if (authSocial == null) {
            throw new UserInfoNotExistException();
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


    @PostMapping("/save/{artId}")
    @Operation(description = "저장하기 / 저장취소(두번선택)")
    public ApiResponse<?> save(@PathVariable @Schema(description = "저장할/저장취소할 공연ID") String artId) {
        User user = securityUserInfo();
        Optional<ArtList> artList = artService.findByArtId(artId);
        if (artList.isEmpty()) {
            throw new ItemNotFoundException();
        }
        if (memberService.updateSaveHist(user, artList.get())) {
            return ApiResponse.success("data", "저장 완료");
        } else {
            return ApiResponse.success("data", "저장취소 완료");
        }

    }

    @GetMapping("/user/mylist")
    @Operation(description = "저장한 연극 목록")
    public ApiResponse<?> saveList(@PageableDefault(size = 6, sort = "artId", direction = Sort.Direction.DESC) Pageable pagable) {
        User user = securityUserInfo();
        Page<ArtListDTO> artList = artService.convertArtList(memberService.retrieveMySaveList(pagable, user));
        return ApiResponse.success("data", artList);
    }

    @GetMapping("/user/prefer")
    @Operation(description = "선호 장르 목록 (map)")
    public ApiResponse<?> preferGenre(){
        User user = securityUserInfo();
        return ApiResponse.success("data", memberService.retrievePreferGenreCount(user));
    }

    @GetMapping("/suggest")
    @Operation(description = "추천 공연 목록")
    public ApiResponse<?> retrieveListSuggestList(){
        User user = securityUserInfo();
        // 유저가 저장한 공연이 세개 이하면 빈 값 반환
        Long count = memberService.countSaveHistByUser(user);
        if (count < 3) {
            throw new NotEnoughDataException();
        } else {
            List<ArtListDTO> artList = artService.convertArtList(memberService.retrieveListSuggestList(user));
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
