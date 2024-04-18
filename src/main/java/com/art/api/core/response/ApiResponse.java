package com.art.api.core.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final static int SUCCESS = 200;
    private final static int NOT_FOUND = 404;
    private final static int DUPLICATE = 250;
    private final static int EXISTS = 260;
    private final static int FAILED = 500;

    private final static String SUCCESS_MESSAGE = "해당 처리가 정상적으로 완료되었습니다.";
    private final static String NOT_FOUND_MESSAGE = "해당 데이터는 존재하지 않습니다.";
    private final static String EXISTS_MESSAGE = "해당 데이터는 이미 존재합니다.";
    private final static String DUPLICATE_MESSAGE = "해당 데이터는 사용중입니다.";
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";

    private final static String INVALID_ACCESS_TOKEN = "Invalid access token.";
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";

    private final ApiResponseHeader header;
    private final Map<String, T> body;


    public static <T> ApiResponse<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new ApiResponse(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), map);
    }

    public static<T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(new ApiResponseHeader(SUCCESS, message), null);
    }

    public static<T> ApiResponse<T> success(Integer statusCode, String statusMessage) {
        return new ApiResponse(new ApiResponseHeader(statusCode, statusMessage), null);
    }

    public static<T> ApiResponse<T> success(Integer statusCode, String statusMessage, String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new ApiResponse(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), map);
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse(new ApiResponseHeader(FAILED, FAILED_MESSAGE), null);
    }

    public static <T> ApiResponse<T> invalidAccessToken() {
        return new ApiResponse(new ApiResponseHeader(FAILED, INVALID_ACCESS_TOKEN), null);
    }

    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse(new ApiResponseHeader(FAILED, INVALID_REFRESH_TOKEN), null);
    }

    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse(new ApiResponseHeader(FAILED, NOT_EXPIRED_TOKEN_YET), null);
    }

    public static <T> ApiResponse<T> notFoundUser() {
        return new ApiResponse(new ApiResponseHeader(NOT_FOUND, NOT_FOUND_MESSAGE), null);
    }

    public static <T> ApiResponse<T> duplicate() {
        return new ApiResponse<>(new ApiResponseHeader(DUPLICATE, DUPLICATE_MESSAGE), null);
    }

    public static <T> ApiResponse<T> existsCounselor() {
        return new ApiResponse<>(new ApiResponseHeader(EXISTS, EXISTS_MESSAGE), null);
    }
}
