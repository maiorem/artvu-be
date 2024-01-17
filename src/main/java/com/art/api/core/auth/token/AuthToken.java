package com.art.api.core.auth.token;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
@Slf4j
@Getter
@RequiredArgsConstructor
public class AuthToken {

    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, Date expiry_date, Key key) {
        this.key = key;
        this.token = createAuthToken(id, expiry_date);
    }

    AuthToken(String id, String role, Date expiry_date, Key key) {
        this.key = key;
        this.token = createAuthToken(id, role, expiry_date);
    }

    private String createAuthToken(String id, Date expiryDate) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiryDate)
                .compact();

    }

    private String createAuthToken(String id, String role, Date expiryDate) {
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiryDate)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException | io.jsonwebtoken.security.SignatureException | MalformedJwtException |
                ExpiredJwtException | IllegalArgumentException e) {
            log.info("토큰이 유효하지 않습니다.");
            return null;
        }
    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }


}
