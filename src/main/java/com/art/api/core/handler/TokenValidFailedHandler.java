package com.art.api.core.handler;

import com.art.api.core.exception.TokenValidFailedException;
import com.art.api.core.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenValidFailedHandler {

    @ExceptionHandler(value = TokenValidFailedException.class)
    public ApiResponse tokenValidFailHandle(TokenValidFailedException exception) {
        return ApiResponse.success(exception.getStatusCode(), exception.getStatusMessage());
    }

}
