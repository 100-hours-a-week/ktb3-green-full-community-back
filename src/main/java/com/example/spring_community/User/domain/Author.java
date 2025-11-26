package com.example.spring_community.User.domain;

import lombok.Getter;

@Getter
public class Author {
    private Long userId;
    private String nickname;
    private String profileImg;

    public Author(Long userId, String nickname, String profileImg) {
        String profileImgPath = "http://localhost:8080/images/" + profileImg;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImgPath;
    }
}
