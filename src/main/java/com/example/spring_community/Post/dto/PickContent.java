package com.example.spring_community.Post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PickContent {
    private String pickTitle;
    private String pickDetail;
    private Integer pickCount;

    @Builder(toBuilder = true)
    public PickContent(String pickTitle, String pickDetail, Integer pickCount) {
        this.pickTitle = pickTitle;
        this.pickDetail = pickDetail;
        this.pickCount = pickCount;
    }
}
