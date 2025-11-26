package com.example.spring_community.Post.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post_meta")
public class PostMetaEntity {

    @Id
    @Column(name = "post_id")
    private Long postId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "pick_count")
    private Integer pickCount;

    @Column(name = "pick_1_count")
    private Integer pick1Count;

    @Column(name = "pick_2_count")
    private Integer pick2Count;

    @Column(name = "comment_count")
    private Integer commentCount;

    public PostMetaEntity() {};

    @Builder(toBuilder = true)
    public PostMetaEntity(Long postId, PostEntity post, Integer pickCount, Integer pick1Count, Integer pick2Count, Integer commentCount) {
        this.postId = postId;
        this.post = post;
        this.pickCount = pickCount;
        this.pick1Count = pick1Count;
        this.pick2Count = pick2Count;
        this.commentCount = commentCount;
    }

    public static PostMetaEntity of (PostEntity post) {
        return PostMetaEntity.builder()
                .post(post).pickCount(0).pick1Count(0).pick2Count(0).commentCount(0).build();
    }

    public void increasePick1Count() {
        this.pick1Count++;
        increaseTotalPickCount();
    }

    public void decreasePick1Count() {
        this.pick1Count--;
        decreaseTotalPickCount();
    }

    public void increasePick2Count() {
        this.pick2Count++;
        increaseTotalPickCount();
    }

    public void decreasePick2Count() {
        this.pick2Count--;
        decreaseTotalPickCount();
    }

    public void increaseTotalPickCount() { this.pickCount++; }

    public void decreaseTotalPickCount() { this.pickCount--; }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        this.commentCount--;
    }

}
