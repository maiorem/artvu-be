package com.art.api.core.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final static int SUCCESS = 200;
    private final static String SUCCESS_MESSAGE = "해당 처리가 정상적으로 완료되었습니다.";

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
}
