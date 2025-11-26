package com.example.spring_community.Auth.jwt;

import com.example.spring_community.Auth.dto.AuthUserDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthFilter extends HttpFilter {
    private final JwtUtils jwtUtils;
    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String auth = request.getHeader("Authorization");

        if (auth != null && auth.startsWith("Bearer ")) {
            String accessToken = auth.substring(7);
            try {
                Claims claims = jwtUtils.parseAndValidate(accessToken);
                Long userId = claims.get("userId", Number.class).longValue();
                AuthUserDto authUserDto = new AuthUserDto(userId);
                request.setAttribute("authUser", authUserDto);
            }
            catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("""
                    {"status":401,"code":"UNAUTHORIZED_TOKEN","message":"토큰이 유효하지 않아 유저 정보를 알 수 없습니다."}
                """);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}