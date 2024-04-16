package com.art.api.user.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SocialJoinType {
    APPLE(1), GOOGLE(2), KAKAO(3), NAVER(4);

    private final int code;

    public static SocialJoinType of(int code) {
        return Arrays.stream(SocialJoinType.values())
                .filter(r -> r.getCode()==code).findAny().get();
    }
}
