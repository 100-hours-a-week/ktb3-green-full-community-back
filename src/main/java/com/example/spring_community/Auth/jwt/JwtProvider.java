package com.example.spring_community.Auth.jwt;

import com.example.spring_community.Auth.dto.CustomUserDetails;
import com.example.spring_community.Auth.dto.TokenDto;
import com.example.spring_community.Auth.redis.RedisDao;
import com.example.spring_community.Auth.security.exception.JwtAuthenticationException;
import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Getter
@Component
public class JwtProvider {

    private final SecretKey secretKey;

    @Value("${jwt.access-token-expiration}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh-token-expiration}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private final RedisDao redisDao;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, RedisDao redisDao) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.redisDao = redisDao;
    }

    public TokenDto generateToken(String username, String authorities) {

        String accessToken = generateAccessToken(username, authorities);
        String refreshToken = generateRefreshToken(username);

        redisDao.setValues(username, refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRATION_TIME));

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateAccessToken(String username, String authorities) {

        long now = System.currentTimeMillis();
        Date expiration = new Date(now + ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .claim("authorities", authorities)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(secretKey)
                .compact();

    }

    public String generateRefreshToken(String username) {

        long now = System.currentTimeMillis();
        Date expiration = new Date(now + REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();

    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);

        if (claims.get("authorities") == null) {
            throw new JwtAuthenticationException("유효한 권한이 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("authorities").toString().split(","))
                .map(SimpleGrantedAuthority::new).toList();

        Long userId = Long.valueOf(claims.getSubject());

        CustomUserDetails principal = new CustomUserDetails(userId, authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }

    public Claims parseClaims(String accessToken) {

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        }
        catch(ExpiredJwtException e) {
            return e.getClaims();
        }

    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }
        catch(Exception e) {
            throw new JwtAuthenticationException("유효하지 않은 토큰입니다.");
        }

    }

    public boolean validateRefreshToken(String token) {

        if(!validateToken(token)) return false;

        try {
            String username = getUserNameFromToken(token);
            String redisToken = (String) redisDao.getValues(username);
            return token.equals(redisToken);
        }
        catch(Exception e) {
            return false;
        }
    }

    public String getUserNameFromToken(String token) {

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        }
        catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }

    }

    public void deleteRefreshToken(String username) {

        if (username == null || username.trim().isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        redisDao.deleteValues(username);

    }

}
