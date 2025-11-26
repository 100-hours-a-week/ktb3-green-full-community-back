package com.example.spring_community.Pick.repository;

import com.example.spring_community.Pick.domain.PickEntity;
import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.User.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickRepository extends JpaRepository<PickEntity, Long> {
    Optional<PickEntity> findByPostAndUser(PostEntity post, UserEntity user);
}
