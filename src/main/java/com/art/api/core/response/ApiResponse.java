package com.art.api.core.response;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final static int SUCCESS = 200;
    private final static int NOT_FOUND = 404;
    private final static int DUPLICATE = 250;
    private final static int EXISTS = 260;
    private final static int NOT_EXISTS = 270;
    private final static int NOT_ENOUGH = 300;
    private final static int FAILED = 500;

    private final static String SUCCESS_MESSAGE = "해당 처리가 정상적으로 완료되었습니다.";
    private final static String NOT_FOUND_MESSAGE = "해당 데이터는 존재하지 않습니다.";
    private final static String EXISTS_MESSAGE = "해당 데이터는 이미 존재합니다.";
    private final static String NOT_EXISTS_MESSAGE = "데이터가 존재하지 않습니다. 입력 화면으로 이동합니다.";
    private final static String DUPLICATE_MESSAGE = "해당 데이터는 사용중입니다.";
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";

    private final static String NOT_ENOUGH_DATA = "저장한 공연 데이터가 충분하지 않습니다.";

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
    public static <T> ApiResponse<T> serverFail(Exception exception) {
        Map<String, String> map = new HashMap<>();

        log.error("서버 에러 발생", exception);

        if(exception instanceof JDBCException) {
            map.put("서버 오류 메세지", "JDBC 관련 에러 발생");
        }
        else if (exception instanceof NullPointerException) {
            map.put("서버 오류 메세지", "Null 값 에러 발생");
        }
        else if (exception instanceof JsonParseException) {
            map.put("서버 오류 메세지", "JSON 파싱 에러");
        }
        else {
            map.put("서버 오류 메세지", "백엔드 서버 로그 요청 필요");
        }

        return new ApiResponse(new ApiResponseHeader(FAILED, FAILED_MESSAGE), map);
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

    public static <T> ApiResponse<T> notEnoughData() {
        return new ApiResponse<>(new ApiResponseHeader(NOT_ENOUGH, NOT_ENOUGH_DATA), null);
    }

    public static <T> ApiResponse<T> notExistData() {
        return new ApiResponse<>(new ApiResponseHeader(NOT_FOUND, NOT_FOUND_MESSAGE), null);
    }


    public static <T> ApiResponse<T> notExistsUserInfo() {
        return new ApiResponse<>(new ApiResponseHeader(NOT_EXISTS, NOT_EXISTS_MESSAGE), null);
    }
}
