package com.example.spring_community.Auth.controller;

import com.example.spring_community.Auth.annotation.AuthUser;
import com.example.spring_community.Auth.dto.*;
import com.example.spring_community.Exception.dto.DataResponseDto;
import com.example.spring_community.Exception.dto.ResponseDto;
import com.example.spring_community.Exception.CustomException;
import com.example.spring_community.Exception.ErrorCode;
import com.example.spring_community.Auth.jwt.JwtUtils;
import com.example.spring_community.Auth.service.AuthService;
import com.example.spring_community.Auth.service.BlacklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Auth API", description = "Auth 리소스에 관한 API 입니다.")
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final BlacklistService blacklistService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, BlacklistService blacklistService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.blacklistService = blacklistService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/token")
    @Operation(summary = "로그인", description = "입력받은 이메일과 비밀번호로 로그인을 진행하고 토큰을 발급합니다.")
    public ResponseEntity<DataResponseDto<AuthUserTokenDto>> login(@RequestBody @Valid LoginDto loginDto) {
        AuthUserTokenDto authUsertokenDto = authService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "LOGIN_SUCCESS", "로그인에 성공했습니다.", authUsertokenDto));
    }

    @DeleteMapping("/token")
    @Operation(summary = "로그아웃", description = "토큰 정보로 인증된 사용자를 로그아웃 시킵니다.")
    public ResponseEntity<ResponseDto> logout(@RequestHeader(value="Authorization") String authorization) {
        String accessToken = (authorization != null && authorization.startsWith("Bearer")) ? authorization.substring(7) : null;
        if (accessToken == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        jwtUtils.verifyToken(accessToken);
        blacklistService.blacklist(accessToken, jwtUtils.getAccessExpiration());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, "LOGOUT_SUCCESS", "정상적으로 로그아웃되었습니다."));
    }

    @PostMapping("/token/new")
    @Operation(summary = "토큰 재발급", description = "토큰 정보로 인증된 사용자의 access token을 재발급합니다.")
    public ResponseEntity<DataResponseDto<TokenDto>> refresh(@RequestBody RefreshTokenDto refreshTokenDto, @AuthUser AuthUserDto authUserDto) {
        TokenDto refreshedToken = authService.reissueAccessToken(authUserDto.getUserId(), refreshTokenDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "REFRESH_USER_SUCCESS", "정상적으로 토큰이 재발급되었습니다.", refreshedToken));
    }
}
