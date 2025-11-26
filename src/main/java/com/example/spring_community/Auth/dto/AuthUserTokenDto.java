package com.example.spring_community.Auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthUserTokenDto {

    private final Long userId;
    private final String profileImg;
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public AuthUserTokenDto(Long userId,String profileImg, String accessToken, String refreshToken) {
        String profileImgPath = "http://localhost:8080/images/" + profileImg;
        this.userId = userId;
        this.profileImg = profileImgPath;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
