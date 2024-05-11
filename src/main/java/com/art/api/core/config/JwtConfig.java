package com.art.api.core.config;

import com.art.api.core.auth.token.AuthTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    private final String SECRET = "fdsfhsdkjfhsdjkfs324242hkjvbnckjvhsu";

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(SECRET);
    }

}
