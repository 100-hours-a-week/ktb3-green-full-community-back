package com.example.spring_community.Comment.dto;

import com.example.spring_community.Comment.domain.CommentEntity;
import com.example.spring_community.User.domain.Author;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CommentDto {

    private final Long commentId;
    private final Author author;
    private final String content;
    private final Integer pickNumber;
    private final Instant updatedAt;

    @Builder(toBuilder = true)
    public CommentDto(Long commentId, Author author, String content, Integer pickNumber, Instant updatedAt) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.pickNumber = pickNumber;
        this.updatedAt = updatedAt;
    }

    public static CommentDto of (CommentEntity commentEntity) {
        Author author = new Author(commentEntity.getUser().getUserId(), commentEntity.getUser().getNickname(), commentEntity.getUser().getProfileImg().getFileName());
        return CommentDto.builder()
                .commentId(commentEntity.getCommentId())
                .author(author)
                .content(commentEntity.getContent())
                .pickNumber(commentEntity.getPickNumber())
                .updatedAt(commentEntity.getUpdatedAt())
                .build();
    }
}

