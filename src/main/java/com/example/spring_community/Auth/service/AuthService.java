package com.example.spring_community.Auth.service;

import com.example.spring_community.Auth.dto.AuthUserTokenDto;
import com.example.spring_community.Auth.dto.RefreshTokenDto;
import com.example.spring_community.Auth.dto.TokenDto;
import com.example.spring_community.User.domain.UserEntity;
import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.Auth.jwt.JwtUtils;
import com.example.spring_community.Auth.dto.LoginDto;
import com.example.spring_community.User.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    public AuthUserTokenDto login(LoginDto loginDto) {
        UserEntity userEntity = findValidUser(loginDto);
        String accessToken = jwtUtils.createAccessToken(userEntity);
        String refreshToken = jwtUtils.createRefreshToken(userEntity);
        String profileImg = userEntity.getProfileImg().getFileName();

        return AuthUserTokenDto.builder()
                .userId(userEntity.getUserId())
                .profileImg(profileImg)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto reissueAccessToken(Long userId, RefreshTokenDto refreshTokenDto) {
        if(!jwtUtils.verifyToken(refreshTokenDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_TOKEN);
        }

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtUtils.createAccessToken(userEntity);

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshTokenDto.getRefreshToken())
                .build();
    }

    public UserEntity findValidUser(LoginDto loginDto) {
        UserEntity userEntity = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword()) || !userEntity.getActive()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        return userEntity;
    }
}
