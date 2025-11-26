package com.example.spring_community.Image.dto;

import com.example.spring_community.Image.domain.ImageEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageDto {

    private final Long imageId;

    @Builder
    public ImageDto(Long imageId) {
        this.imageId = imageId;
    }

    public static ImageDto of (ImageEntity imageEntity) {
        return new ImageDto(imageEntity.getImageId());
    }

}
