package com.example.spring_community.Comment.repository;

import com.example.spring_community.Comment.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPost_PostIdOrderByCreatedAtDesc(Long postId);
}
