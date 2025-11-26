package com.example.spring_community.Pick.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PickDto {

    private final Long postId;
    private final Integer pickNumber;

    @Builder(toBuilder = true)
    public PickDto(Long postId, Integer pickNumber) {
        this.postId = postId;
        this.pickNumber = pickNumber;
    }

}
