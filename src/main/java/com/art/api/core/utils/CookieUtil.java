package com.art.api.core.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Optional;

public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name))
                    return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {

        ResponseCookie responseCookie = ResponseCookie.from(name, value)
                .domain(".artvu.co.kr")
                .path("/")
                .httpOnly(true)
                .maxAge(maxAge)
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());

//        Cookie cookie = new Cookie(name, value);
//        cookie.setPath("/");
//        cookie.setDomain(domain);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(maxAge);
//        cookie.setSecure(true);
//        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    ResponseCookie responseCookie = ResponseCookie.from(name, "")
                            .domain(".artvu.co.kr")
                            .path("/")
                            .maxAge(0)
                            .build();
                    response.addHeader("Set-Cookie", responseCookie.toString());
                }
            }
        }
    }

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
