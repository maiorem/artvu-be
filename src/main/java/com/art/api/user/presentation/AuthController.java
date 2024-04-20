package com.art.api.user.presentation;

import com.art.api.core.auth.token.AuthToken;
import com.art.api.core.auth.token.AuthTokenProvider;
import com.art.api.core.config.AppProperties;
import com.art.api.core.exception.ClientUserNotFoundException;
import com.art.api.core.response.ApiResponse;
import com.art.api.core.utils.CookieUtil;
import com.art.api.core.utils.HeaderUtil;
import com.art.api.user.domain.entity.AuthSocial;
import com.art.api.user.domain.entity.RoleType;
import com.art.api.user.domain.entity.SocialJoinType;
import com.art.api.user.domain.entity.User;
import com.art.api.user.domain.model.AuthRequest;
import com.art.api.user.domain.oauth.UserPrincipal;
import com.art.api.user.infrastructure.repository.AuthSocialRepository;
import com.art.api.user.infrastructure.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final AuthSocialRepository authSocialRepository;

    private final static long THREE_DAYS_MSEC = 259200000L;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/login")
    @Operation(hidden = true)
    public ApiResponse clientUserLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getId(), authRequest.getPassword()));

        String userId = authRequest.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(userId, ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry));

        Optional<User> optionalUser = memberRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new ClientUserNotFoundException();
        }
        AuthSocial authSocial = authSocialRepository.findByUser(optionalUser.get());
        if (authSocial == null) {
            authSocial = AuthSocial.builder()
                            .user(optionalUser.get())
                            .socialJoinType(SocialJoinType.KAKAO)
                            .refreshToken(refreshToken.getToken())
                            .build();
            authSocialRepository.saveAndFlush(authSocial);
        } else {
            authSocial.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) (refreshTokenExpiry / 60);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }

    /**
     * 클라이이언트의 Access Token 만료인 경우, Refresh Token 을 이용하여 Access Token 재발급
     */
    @GetMapping("/refresh")
    @Operation(hidden = true)
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // http 요청 패킷 내 쿠키에서 refresh token을 가져옴
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        Optional<User> optionalUser = memberRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new ClientUserNotFoundException();
        }
        // userId refresh token 으로 DB 확인
        AuthSocial authSocial = authSocialRepository.findByUserAndRefreshToken(optionalUser.get(), refreshToken);
        if (authSocial == null) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            authSocial.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return ApiResponse.success("token", newAccessToken.getToken());
    }

}
