package com.art.api.core.handler;

import com.art.api.core.response.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity serverExceptionInvoke(Exception exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ApiResponse.serverFail(exception));
    }

}
