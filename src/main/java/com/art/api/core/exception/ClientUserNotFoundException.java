package com.art.api.core.exception;

import com.art.api.core.response.ErrorCode;

public class ClientUserNotFoundException extends RuntimeException implements ExceptionIfs  {

    private String statusMessage = "해당 계정 정보는 존재하지 않습니다.";

    public ClientUserNotFoundException() {}

    public ClientUserNotFoundException(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public Integer getStatusCode() {
        return ErrorCode.NOT_FOUND.getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }
}
