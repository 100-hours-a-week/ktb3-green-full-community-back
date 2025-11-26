package com.example.spring_community.Comment.service;

import com.example.spring_community.Comment.dto.CommentDto;
import com.example.spring_community.Comment.dto.NewCommentDto;
import com.example.spring_community.Comment.repository.CommentRepository;
import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.Post.domain.PostMetaEntity;
import com.example.spring_community.Post.repository.PostMetaRepository;
import com.example.spring_community.Post.repository.PostRepository;
import com.example.spring_community.User.domain.Author;
import com.example.spring_community.Comment.domain.CommentEntity;
import com.example.spring_community.User.domain.UserEntity;
import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.User.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMetaRepository postMetaRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, PostMetaRepository postMetaRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postMetaRepository = postMetaRepository;
    }

    public void isValidPost (Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<CommentDto> readCommentList(Long postId) {
        List<CommentEntity> commentEntityList = commentRepository.findByPost_PostIdOrderByCreatedAtDesc(postId);
        return commentEntityList.stream().map(CommentDto::of).toList();
    }

    @Transactional
    public CommentDto createComment(Long userId, Long postId, NewCommentDto newCommentDto) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        CommentEntity newCommentEntity = CommentEntity.builder()
                        .post(postEntity).user(userEntity)
                        .content(newCommentDto.getContent())
                        .pickNumber(newCommentDto.getPickNumber())
                        .build();

        commentRepository.save(newCommentEntity);

        PostMetaEntity postMetaEntity = postMetaRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postMetaEntity.increaseCommentCount();
        postMetaRepository.save(postMetaEntity);

        return CommentDto.of(newCommentEntity);
    }

    @Transactional
    public CommentDto updateComment(Long userId, Long postId, Long commentId, NewCommentDto newCommentDto) {

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!userId.equals(commentEntity.getUser().getUserId())) {
            throw new CustomException(ErrorCode.COMMENT_UPDATE_FORBIDDEN_USER);
        }

        commentEntity.setContent(newCommentDto.getContent());

        commentRepository.save(commentEntity);

        return CommentDto.of(commentEntity);
    }

    @Transactional
    public void deleteComment(Long userId, Long postId, Long commentId) {

        UserEntity deleteUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!userId.equals(commentEntity.getUser().getUserId())) {
            throw new CustomException(ErrorCode.COMMENT_DELETE_FORBIDDEN_USER);
        }

        commentRepository.deleteById(commentId);

        PostMetaEntity postMetaEntity = postMetaRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postMetaEntity.decreaseCommentCount();
        postMetaRepository.save(postMetaEntity);
    }
}
