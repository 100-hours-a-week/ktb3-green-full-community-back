package com.example.spring_community.Post.domain;

import com.example.spring_community.User.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pick_1", nullable = false)
    private String pick1;

    @Column(name = "pick_1_detail")
    private String pick1Detail;

    @Column(name = "pick_2", nullable = false)
    private String pick2;

    @Column(name = "pick_2_detail")
    private String pick2Detail;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private PostMetaEntity postMetaEntity;

    protected PostEntity() {}

    @Builder(toBuilder = true)
    public PostEntity(Long postId, UserEntity user, String title, String pick1, String pick2, String pick1Detail, String pick2Detail, Instant createdAt, Instant updatedAt, PostMetaEntity postMetaEntity) {
        this.postId = postId;
        this.user = user;
        this.title = title;
        this.pick1 = pick1;
        this.pick2 = pick2;
        this.pick1Detail = pick1Detail;
        this.pick2Detail = pick2Detail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postMetaEntity = postMetaEntity;
    }

    public void setPostMetaEntity(PostMetaEntity postMetaEntity) {
        this.postMetaEntity = postMetaEntity;
    }

}
