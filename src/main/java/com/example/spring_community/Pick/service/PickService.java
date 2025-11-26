package com.example.spring_community.Pick.service;

import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.Pick.domain.PickEntity;
import com.example.spring_community.Pick.dto.PickDto;
import com.example.spring_community.Pick.repository.PickRepository;
import com.example.spring_community.Post.domain.PostEntity;
import com.example.spring_community.Post.domain.PostMetaEntity;
import com.example.spring_community.Post.repository.PostMetaRepository;
import com.example.spring_community.Post.repository.PostRepository;
import com.example.spring_community.User.domain.UserEntity;
import com.example.spring_community.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PickService {

    private final PickRepository pickRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMetaRepository postMetaRepository;

    public PickService(PickRepository pickRepository, PostRepository postRepository, UserRepository userRepository, PostMetaRepository postMetaRepository) {
        this.pickRepository = pickRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMetaRepository = postMetaRepository;
    }

    @Transactional
    public PickDto addPick(Long postId, Long userId, Integer pickNumber) {

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(pickNumber != 1 && pickNumber != 2) throw new CustomException(ErrorCode.INVALID_PICK_NUMBER);

        isPicked(postId, userId).ifPresent(pick -> {
            throw new CustomException(ErrorCode.DUPLICATED_PICK);
        });

        PickEntity pickEntity = PickEntity.builder()
                .post(postEntity).user(userEntity).pickNumber(pickNumber).build();

        pickRepository.save(pickEntity);

        PostMetaEntity postMetaEntity = postMetaRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if(pickNumber == 1) postMetaEntity.increasePick1Count();
        else postMetaEntity.increasePick2Count();

        postMetaRepository.save(postMetaEntity);

        return PickDto.builder().postId(postId).pickNumber(pickNumber).build();
    }

    @Transactional
    public PickDto deletePick(Long postId, Long userId, Integer pickNumber) {

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(pickNumber != 1 && pickNumber != 2) throw new CustomException(ErrorCode.INVALID_PICK_NUMBER);

        PickEntity pickEntity = isPicked(postId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PICK_NOT_FOUND));

        pickRepository.delete(pickEntity);

        PostMetaEntity postMetaEntity = postMetaRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_META_NOT_FOUND));

        if(pickNumber == 1) postMetaEntity.increasePick1Count();
        else postMetaEntity.increasePick2Count();

        postMetaRepository.save(postMetaEntity);

        return PickDto.builder().postId(postId).pickNumber(pickNumber).build();

    }

    @Transactional
    public PickDto getPickNumber(Long postId, Long userId) {

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Integer pickNumber = pickRepository.findByPostAndUser(postEntity, userEntity)
                .map(PickEntity::getPickNumber)
                .orElse(0);

        return PickDto.builder().postId(postId).pickNumber(pickNumber).build();
    }

    public Optional<PickEntity> isPicked(Long postId, Long userId) {

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return pickRepository.findByPostAndUser(postEntity, userEntity);
    }



}
