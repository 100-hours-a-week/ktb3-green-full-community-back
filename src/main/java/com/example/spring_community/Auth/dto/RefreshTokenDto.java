package com.example.spring_community.Auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshTokenDto {

    private final String refreshToken;

    @Builder
    public RefreshTokenDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
