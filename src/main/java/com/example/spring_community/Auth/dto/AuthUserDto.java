package com.example.spring_community.Auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthUserDto {

    private final Long userId;

    @Builder
    public AuthUserDto(Long userId) {
        this.userId = userId;
    }
}
