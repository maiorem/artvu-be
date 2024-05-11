package com.art.api.core.exception;

import com.art.api.core.response.ErrorCode;

public class NotEnoughDataException  extends RuntimeException implements ExceptionIfs{

    private String statusMessage = "데이터가 충분하지 않습니다.";

    public NotEnoughDataException() {}

    public NotEnoughDataException(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public Integer getStatusCode() {
        return ErrorCode.NOT_ENOUGH.getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return ErrorCode.NOT_ENOUGH.getStatusMessage();
    }
}
