package com.example.spring_community.User.repository;

import com.example.spring_community.User.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);
    public Boolean existsByEmail(String email);
    public Boolean existsByNickname(String Nickname);
    Page<UserEntity> findAllByOrderByUserIdDesc(Pageable pageable);
}
