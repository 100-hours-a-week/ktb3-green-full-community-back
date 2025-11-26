package com.example.spring_community.User.dto;

import com.example.spring_community.User.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileDto {

    private final Long userId;
    private final String email;
    private final String nickname;
    private final String profileImg;

    @Builder
    public UserProfileDto(Long userId, String email, String nickname, String profileImg) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public static UserProfileDto of (UserEntity user) {
        String profileImgPath = "http://localhost:8080/images/" + user.getProfileImg();
        return new UserProfileDto(user.getUserId(), user.getEmail(), user.getNickname(), profileImgPath);
    }
}
