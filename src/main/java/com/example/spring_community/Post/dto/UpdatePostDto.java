package com.example.spring_community.Post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class UpdatePostDto {

    private String title;
    private String pick1Title;
    private String pick1Detail;
    private String pick2Title;
    private String pick2Detail;

}
