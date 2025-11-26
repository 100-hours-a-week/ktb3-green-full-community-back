package com.example.spring_community.Auth.jwt;

import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.User.domain.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Getter
@Component
public class JwtUtils {
    private final SecretKey secretKey;

    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtUtils(@Value("${jwt.secret}") String secretKey,
                    @Value("${jwt.access-token-expiration}") long accessExpiration,
                    @Value("${jwt.refresh-token-expiration}") long refreshExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String createAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .subject(String.valueOf(userEntity.getUserId()))
                .claim("userId", userEntity.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(UserEntity userEntity) {
        return Jwts.builder()
                .subject(String.valueOf(userEntity.getUserId()))
                .claim("userId", userEntity.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey)
                .compact();
    }

    public Boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_TOKEN);
        }
    }

    public Claims parseAndValidate(String accessToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }
}
