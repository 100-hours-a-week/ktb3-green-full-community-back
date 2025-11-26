package com.example.spring_community.Comment.domain;

import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.User.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "pick_number", nullable = false)
    private Integer pickNumber;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

    public CommentEntity() {};

    @Builder(toBuilder = true)
    public CommentEntity(Long commentId, PostEntity post, UserEntity user, Integer pickNumber, String content, Instant createdAt, Instant updatedAt) {
        this.commentId = commentId;
        this.post = post;
        this.user = user;
        this.pickNumber = pickNumber;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

}