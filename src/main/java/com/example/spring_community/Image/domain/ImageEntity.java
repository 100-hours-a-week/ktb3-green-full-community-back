package com.example.spring_community.Image.domain;

import com.example.spring_community.User.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long imageId;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

    protected ImageEntity() {}

    @Builder(toBuilder = true)
    public ImageEntity(Long imageId, String fileName, Instant createdAt, Instant updatedAt) {
        this.imageId = imageId;
        this.fileName = fileName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
