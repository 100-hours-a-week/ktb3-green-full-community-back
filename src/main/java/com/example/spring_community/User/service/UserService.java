package com.example.spring_community.User.service;

import com.example.spring_community.Image.domain.ImageEntity;
import com.example.spring_community.Image.dto.ImageDto;
import com.example.spring_community.Image.service.ImageService;
import com.example.spring_community.User.dto.UpdateUserProfileDto;
import com.example.spring_community.User.dto.UpdateUserPwDto;
import com.example.spring_community.User.dto.CreateUserDto;
import com.example.spring_community.User.domain.UserEntity;
import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.User.dto.UserProfileDto;
import com.example.spring_community.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @Transactional
    public UserProfileDto getUserProfile(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserProfileDto.of(userEntity);
    }

    @Transactional
    public Page<UserProfileDto> getAllUsersProfile(Pageable pageable) {
        return userRepository.findAllByOrderByUserIdDesc(pageable).map(UserProfileDto::of);
    }

    @Transactional
    public UserProfileDto updateUserProfile(Long userId, UpdateUserProfileDto updateUserProfileDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String newNickname = Optional.ofNullable(updateUserProfileDto.getNickname())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElse(userEntity.getNickname());

        MultipartFile profileImg = updateUserProfileDto.getProfileImg();
        ImageEntity imageEntity = null;
        if(profileImg != null && !profileImg.isEmpty()) {
            imageEntity = imageService.uploadProfileImage(profileImg);
        }

        userEntity.setNickname(newNickname);
        userEntity.setProfileImg(imageEntity);

        return UserProfileDto.of(userEntity);
    }

    @Transactional
    public void updateUserPw(Long userId, UpdateUserPwDto updateUserPwDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(updateUserPwDto.getPassword(), userEntity.getPassword())) {
            throw new CustomException(ErrorCode.DUPLICATED_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(updateUserPwDto.getPassword());
        userEntity.setPassword(encodedPassword);
    }

    @Transactional
    public UserProfileDto signup(CreateUserDto createUserDto) {
        if (!createUserDto.getPassword().equals(createUserDto.getCheckPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_USER);
        }

        if (userRepository.existsByNickname(createUserDto.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }

        MultipartFile profileImg = createUserDto.getProfileImg();
        ImageEntity imageEntity = null;
        if(profileImg != null && !profileImg.isEmpty()) {
            imageEntity = imageService.uploadProfileImage(profileImg);
        }

        String encodedPassword = passwordEncoder.encode(createUserDto.getPassword());
        UserEntity userEntity = UserEntity.builder()
                .email(createUserDto.getEmail())
                .password(encodedPassword)
                .nickname(createUserDto.getNickname())
                .profileImg(imageEntity)
                .active(true)
                .role("USER")
                .build();

        userRepository.save(userEntity);

        return UserProfileDto.of(userEntity);
    }

    @Transactional
    public void withdrawUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!userEntity.getActive()) {
            throw new CustomException(ErrorCode.DUPLICATED_WITHDRAW);
        }

        userEntity.setActive(false);
    }

}
