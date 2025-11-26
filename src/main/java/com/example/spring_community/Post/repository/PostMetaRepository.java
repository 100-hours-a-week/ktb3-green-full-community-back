package com.example.spring_community.Post.repository;

import com.example.spring_community.Post.domain.PostMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostMetaRepository extends JpaRepository<PostMetaEntity, Long> {
}
