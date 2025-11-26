package com.example.spring_community.Post.service;

import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.Post.domain.PostMetaEntity;
import com.example.spring_community.Post.dto.*;
import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.Post.repository.PostMetaRepository;
import com.example.spring_community.Post.repository.PostRepository;
import com.example.spring_community.User.domain.UserEntity;
import com.example.spring_community.User.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMetaRepository postMetaRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostMetaRepository postMetaRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMetaRepository = postMetaRepository;
    }

    @Transactional(readOnly = true)
    public DetailPostDto readPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return DetailPostDto.of(postEntity, postEntity.getPostMetaEntity());
    }

    @Transactional(readOnly = true)
    public Page<PostItemDto> readPostPage(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDescPostIdDesc(pageable).map(PostItemDto::from);
    }

    @Transactional
    public NewPostDto createPost(Long userId, NewPostDto newPostDto) {

        UserEntity postUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        PostEntity newPostEntity = PostEntity.builder()
                .title(newPostDto.getTitle())
                .pick1(newPostDto.getPick1Title()).pick1Detail(newPostDto.getPick1Detail())
                .pick2(newPostDto.getPick2Title()).pick2Detail((newPostDto.getPick2Detail()))
                .user(postUser)
                .build();

        postRepository.save(newPostEntity);

        PostMetaEntity meta = PostMetaEntity.of(newPostEntity);
        postMetaRepository.save(meta);

        return NewPostDto.of(newPostEntity);
    }

    @Transactional
    public NewPostDto updatePost(Long userId, Long postId, UpdatePostDto updatePostDto) {

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!userId.equals(postEntity.getUser().getUserId())) {
            throw new CustomException(ErrorCode.POST_UPDATE_FORBIDDEN_USER);
        }

//        String updateTitle = Optional.ofNullable(updatePostDto.getTitle())
//                .map(String::trim).filter(s -> !s.isEmpty()).orElse(postEntity.getTitle());
//
//        String updateContent = Optional.ofNullable(updatePostDto.getContent())
//                .map(String::trim).filter(s -> !s.isEmpty()).orElse(postEntity.getContent());
//
//        String updatePostImg = Optional.ofNullable(updatePostDto.getPostImg())
//                .map(String::trim).filter(s -> !s.isEmpty()).orElse(postEntity.getPostImg());

        PostEntity newPostEntity = postEntity.toBuilder()
                .title(updatePostDto.getTitle())
                .pick1(updatePostDto.getPick1Title())
                .pick1Detail(updatePostDto.getPick1Detail())
                .pick2(updatePostDto.getPick2Title())
                .pick2Detail((updatePostDto.getPick2Detail()))
                .build();

        postRepository.save(newPostEntity);

        return NewPostDto.of(newPostEntity);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {

        UserEntity deleteUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!userId.equals(postEntity.getUser().getUserId())) {
            throw new CustomException(ErrorCode.POST_DELETE_FORBIDDEN_USER);
        }

        postRepository.deleteById(postId);
    }

}
