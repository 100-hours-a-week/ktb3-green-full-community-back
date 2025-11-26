package com.example.spring_community.User.dto;

import lombok.Getter;

@Getter
public class UpdateUserPwDto {
    private String password;

    public UpdateUserPwDto(String password) {
        this.password = password;
    }
}
