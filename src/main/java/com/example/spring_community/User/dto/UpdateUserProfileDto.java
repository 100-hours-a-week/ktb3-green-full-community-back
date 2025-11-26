package com.example.spring_community.User.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserProfileDto {
    private String nickname;
    private MultipartFile profileImg;

    public UpdateUserProfileDto(String nickname, MultipartFile profileImg) {
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
