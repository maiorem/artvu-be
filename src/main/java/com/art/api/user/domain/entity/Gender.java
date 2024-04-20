package com.art.api.user.domain.entity;

public enum Gender {
    MALE("M"), FEMALE("F");
    String code;

    Gender(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

}
