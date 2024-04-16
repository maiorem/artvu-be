package com.art.api.user.application;

import com.art.api.core.exception.OAuthProviderMissMatchException;
import com.art.api.user.domain.entity.AuthSocial;
import com.art.api.user.domain.entity.LoginType;
import com.art.api.user.domain.entity.SocialJoinType;
import com.art.api.user.domain.entity.User;
import com.art.api.user.domain.oauth.OAuth2UserInfo;
import com.art.api.user.domain.oauth.OAuth2UserInfoFactory;
import com.art.api.user.domain.oauth.UserPrincipal;
import com.art.api.user.infrastructure.repository.AuthSocialRepository;
import com.art.api.user.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final AuthSocialRepository authSocialRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User user = super.loadUser(userRequest);
//
//        try {
//            return this.process(userRequest, user);
//        } catch (Exception e) {
//            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
//        }
        return null;
    }

//    public OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
//        SocialJoinType type = SocialJoinType
//                .valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
//
//        OAuth2UserInfo clientUserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(type, user.getAttributes());
//        Optional<User> clientUserOptional = memberRepository.findByUserId(clientUserInfo.getId());
//
//        User clientUser;
//        AuthSocial auth;
//        String userId;
//
//        if (clientUserOptional.isPresent()) {
//            Optional<AuthSocial> authOptinal = authSocialRepository.findByUser(clientUserOptional.get());
//            if (authOptinal.isEmpty()) {
//
//            }
//            clientUser = clientUserOptional.get();
//            auth = authOptinal.get();
//            userId = clientUser.getUserId();
//            SocialJoinType clientType = auth.getSocialJoinType();
//
//            if (type != clientType) {
//                throw new OAuthProviderMissMatchException(
//                        "Looks like you're signed up with " + type +
//                                " account. Please use your " + auth.getSocialJoinType() + " account to login."
//                );
//            }
//            updateUser(clientUser, clientUserInfo);
//        } else {
//            clientUser = createUser(clientUserInfo, type);
//            auth = authSocialRepository.findByUser(clientUser).get();
//            userId = clientUser.getUserId();
//            log.info("savedClientUser = {}", clientUser);
//        }
//        return UserPrincipal.create(auth, user.getAttributes(), userId);
//    }


    private User createUser(OAuth2UserInfo clientUserInfo, SocialJoinType type) {
        LocalDateTime now = LocalDateTime.now();
        User clientUser = User.builder()
                .userId(clientUserInfo.getId())
                .userName(clientUserInfo.getName())
                .loginType(LoginType.SOCIAL)
                .build();


        memberRepository.saveAndFlush(clientUser);
//        authSocialRepository.saveAndFlush()

        return memberRepository.saveAndFlush(clientUser);
    }

//    private User updateUser(User clientUser, OAuth2UserInfo clientUserInfo) {
//        clientUser.setProfileImageUrl(clientUserInfo.getImageUrl());
//        memberRepository.saveAndFlush(clientUser);
//        return clientUser;
//    }
}
