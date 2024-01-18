package com.art.api.core.exception;

import com.art.api.core.response.ErrorCode;
import com.art.api.core.response.ExceptionIf;

public class TokenValidFailedException extends RuntimeException implements ExceptionIf {

    private final String STATUS_MESSAGE = "토큰이 유효하지 않습니다.";

    @Override
    public Integer getStatusCode() {
        return ErrorCode.NOT_FOUND.getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return STATUS_MESSAGE;
    }

}
