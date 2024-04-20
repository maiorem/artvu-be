package com.art.api.user.presentation;

import com.art.api.core.exception.ClientUserNotFoundException;
import com.art.api.core.exception.UserInfoNotExistException;
import com.art.api.core.response.ApiResponse;
import com.art.api.user.application.MemberService;
import com.art.api.user.domain.entity.AuthSocial;
import com.art.api.user.domain.entity.MemberProfile;
import com.art.api.user.domain.entity.User;
import com.art.api.user.domain.entity.UserAuth;
import com.art.api.user.domain.model.LoginResponse;
import com.art.api.user.domain.model.MypageResponse;
import com.art.api.user.domain.model.UpdateUserInfoRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

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
        MypageResponse response = MypageResponse.of(clientUser.getUserId(), clientUser.getUserName(), profile.getNickNm(), authSocial.getProfileImgUrl(), userAuth.getEmail(), userAuth.getSex());
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
