package com.example.spring_community.Comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NewCommentDto {

    private String content;
    private Integer pickNumber;

    @Builder(toBuilder = true)
    public NewCommentDto(String content, Integer pickNumber) {
        this.pickNumber = pickNumber;
        this.content = content;
    }
}
