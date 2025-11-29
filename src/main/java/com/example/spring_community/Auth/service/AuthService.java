package com.example.spring_community.Auth.service;

import com.example.spring_community.Auth.dto.AuthUserTokenDto;
import com.example.spring_community.Auth.dto.RefreshTokenDto;
import com.example.spring_community.Auth.dto.TokenDto;
import com.example.spring_community.User.domain.UserEntity;
import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.Auth.jwt.JwtProvider;
import com.example.spring_community.Auth.dto.LoginDto;
import com.example.spring_community.User.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }
    public AuthUserTokenDto login(LoginDto loginDto) {

        UserEntity user = findValidUser(loginDto);

        String username = String.valueOf(user.getUserId());
        String authorities = user.getRole().equals("ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";

        TokenDto token = jwtProvider.generateToken(username, authorities);

        String profileImg = user.getProfileImg().getFileName();

        return AuthUserTokenDto.builder()
                .userId(user.getUserId())
                .profileImg(profileImg)
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    public TokenDto reissueAccessToken(Long userId, RefreshTokenDto refreshTokenDto) {
        if(!jwtProvider.validateToken(refreshTokenDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_TOKEN);
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String username = String.valueOf(user.getUserId());
        String authorities = user.getRole().equals("ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";

        String newAccessToken = jwtProvider.generateAccessToken(username, authorities);

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
