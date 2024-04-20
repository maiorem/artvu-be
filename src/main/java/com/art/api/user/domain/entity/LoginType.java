package com.art.api.user.domain.entity;

public enum LoginType {
    IDPW(1), SOCIAL(2);

    private int code;

    LoginType(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
