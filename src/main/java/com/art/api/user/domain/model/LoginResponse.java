package com.art.api.user.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String userId;
    private String userName;

    public LoginResponse(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static LoginResponse of(String userId, String userName) {
        return new LoginResponse(userId, userName);
    }

}
