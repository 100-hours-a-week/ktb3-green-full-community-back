package com.example.spring_community.Post.repository;

import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.User.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{
    Page<PostEntity> findAllByOrderByCreatedAtDescPostIdDesc(Pageable pageable);
}
