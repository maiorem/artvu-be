package com.art.api.user.domain.oauth;

import com.art.api.user.domain.entity.SocialJoinType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(SocialJoinType type, Map<String, Object> attributes) {
        switch (type) {
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException(
                    "Social Join Type not supported: " + type);
        }
    }

}
