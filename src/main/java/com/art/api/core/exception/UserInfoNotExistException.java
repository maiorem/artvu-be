package com.art.api.core.exception;

import com.art.api.core.response.ErrorCode;

public class UserInfoNotExistException  extends RuntimeException implements ExceptionIfs  {

    private String statusMessage = "해당 계정 정보가 완성되지 않았습니다.";

    public UserInfoNotExistException() {}

    public UserInfoNotExistException(String statusMessage) {
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
