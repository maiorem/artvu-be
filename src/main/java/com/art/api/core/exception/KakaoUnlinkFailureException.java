package com.art.api.core.exception;

import com.art.api.core.response.ErrorCode;

public class KakaoUnlinkFailureException extends RuntimeException implements ExceptionIfs {

    private String statusMessage;

    public KakaoUnlinkFailureException() {}

    public KakaoUnlinkFailureException(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public Integer getStatusCode() {
        return ErrorCode.KAKAO_UNLINK_FAIL.getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return ErrorCode.KAKAO_UNLINK_FAIL.getStatusMessage();
    }
}
