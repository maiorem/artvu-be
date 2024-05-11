package com.art.api.core.exception;

import com.art.api.core.response.ErrorCode;

public class ItemNotFoundException  extends RuntimeException implements ExceptionIfs{

    private String statusMessage = "대상을 찾을 수 없습니다.";

    public ItemNotFoundException() {}

    public ItemNotFoundException(String statusMessage) {
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
