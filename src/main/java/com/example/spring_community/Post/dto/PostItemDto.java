package com.example.spring_community.Post.dto;

import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.User.domain.Author;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostItemDto {
    private final Long postId;
    private final String title;
    private final Author author;
    private final PickContent pick1;
    private final PickContent pick2;
    private final Integer pickCount;

    @Builder(toBuilder = true)
    public PostItemDto(Long postId, String title, Author author, PickContent pick1, PickContent pick2, Integer pickCount){
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.pick1 = pick1;
        this.pick2 = pick2;
        this.pickCount = pickCount;
    }

    public static PostItemDto from(PostEntity post) {
        String profileImg = "/images" + post.getUser().getProfileImg().getFileName();
        Author author = new Author(post.getUser().getUserId(), post.getUser().getNickname(), profileImg);
        PickContent pick1 = PickContent.builder().pickTitle(post.getPick1()).pickDetail(post.getPick1Detail()).pickCount(post.getPostMetaEntity().getPick1Count()).build();
        PickContent pick2 = PickContent.builder().pickTitle(post.getPick2()).pickDetail(post.getPick2Detail()).pickCount(post.getPostMetaEntity().getPick2Count()).build();
        return PostItemDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .author(author)
                .pick1(pick1).pick2(pick2)
                .pickCount(post.getPostMetaEntity().getPickCount())
                .build();
    }

}
