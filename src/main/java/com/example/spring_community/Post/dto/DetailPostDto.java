package com.example.spring_community.Post.dto;

import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.Post.domain.PostMetaEntity;
import com.example.spring_community.User.domain.Author;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class DetailPostDto {
    private final Long postId;
    private final String title;
    private final Author author;
    private final PickContent pick1;
    private final PickContent pick2;
    private final Integer pickCount;
    private final Integer comments;
    private final Instant updatedAt;

    @Builder(toBuilder = true)
    public DetailPostDto(Long postId, String title, Author author, PickContent pick1, PickContent pick2, Integer pickCount, Integer comments, Instant updatedAt){
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.pick1 = pick1;
        this.pick2 = pick2;
        this.pickCount = pickCount;
        this.comments = comments;
        this.updatedAt = updatedAt;
    }

    public static DetailPostDto of (PostEntity post, PostMetaEntity postMeta) {
        Author author = new Author(post.getUser().getUserId(), post.getUser().getNickname(), post.getUser().getProfileImg().getFileName());
        PickContent pick1 = PickContent.builder().pickTitle(post.getPick1()).pickDetail(post.getPick1Detail()).pickCount(post.getPostMetaEntity().getPick1Count()).build();
        PickContent pick2 = PickContent.builder().pickTitle(post.getPick2()).pickDetail(post.getPick2Detail()).pickCount(post.getPostMetaEntity().getPick2Count()).build();
        return DetailPostDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .author(author)
                .pick1(pick1).pick2(pick2)
                .pickCount(postMeta.getPickCount())
                .comments(postMeta.getCommentCount())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
