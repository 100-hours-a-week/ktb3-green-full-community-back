package com.example.spring_community.Post.dto;

import com.example.spring_community.Post.domain.PostEntity;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
@Getter
public class NewPostDto {

    @NotBlank(message="제목을 입력해주세요.")
    @Size(max = 26, message = "제목은 최대 26자까지 작성 가능합니다.")
    private final String title;

    @NotBlank(message="본문을 입력해주세요.")
    private final String pick1Title;

    private final String pick1Detail;

    @NotBlank(message="본문을 입력해주세요.")
    private final String pick2Title;

    private final String pick2Detail;

    @Builder(toBuilder = true)
    public NewPostDto(String title, String pick1Title, String pick1Detail, String pick2Title, String pick2Detail) {
        this.title = title;
        this.pick1Title = pick1Title;
        this.pick1Detail = pick1Detail;
        this.pick2Title = pick2Title;
        this.pick2Detail = pick2Detail;
    }

    public static NewPostDto of (PostEntity post) {
        return NewPostDto.builder()
                .title(post.getTitle())
                .pick1Title(post.getPick1())
                .pick1Detail(post.getPick1Detail())
                .pick2Title(post.getPick2())
                .pick2Detail(post.getPick2Detail())
                .build();
    }
}
