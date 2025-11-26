package com.example.spring_community.Pick.domain;

import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.User.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "picks")
public class PickEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long pickId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "pick_number")
    private Integer pickNumber;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    public PickEntity() {};

    @Builder(toBuilder = true)
    public PickEntity(Long pickId, PostEntity post, UserEntity user, Integer pickNumber, Instant createdAt) {
        this.pickId = pickId;
        this.post = post;
        this.user = user;
        this.pickNumber = pickNumber;
    }

}
