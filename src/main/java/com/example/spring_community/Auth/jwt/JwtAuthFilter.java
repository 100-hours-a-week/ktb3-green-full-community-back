package com.example.spring_community.Auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Slf4j
public class JwtAuthFilter extends HttpFilter {
    private final JwtProvider jwtProvider;
    public JwtAuthFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("[JwtAuthFilter] {} {} authHeader={}", request.getMethod(), request.getRequestURI(), request.getHeader("Authorization"));

        String auth = request.getHeader("Authorization");
        String accessToken = null;

        if(auth != null && auth.startsWith("Bearer ")) {
            accessToken = auth.substring(7);
        }

        if(accessToken != null) {
            if(jwtProvider.validateToken(accessToken)) {
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);

    }
}