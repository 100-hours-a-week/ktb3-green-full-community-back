package com.example.spring_community.Image.repository;

import com.example.spring_community.Image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
