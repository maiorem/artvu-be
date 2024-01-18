package com.art.api.core.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponseHeader {
    private int code;
    private String message;

    public ApiResponseHeader(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
