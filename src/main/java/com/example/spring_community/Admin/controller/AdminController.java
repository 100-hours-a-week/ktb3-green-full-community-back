package com.example.spring_community.Admin.controller;

import com.example.spring_community.Exception.dto.DataResponseDto;
import com.example.spring_community.User.dto.UserProfileDto;
import com.example.spring_community.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Admin API", description = "Admin 권한을 가진 유저만 접근 가능한 API 입니다.")
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profile")
    @Operation(summary = "사용자 프로필 조회", description = "사용자의 프로필 정보를 조회합니다")
    public ResponseEntity<DataResponseDto<Page<UserProfileDto>>> getUserProfile(Pageable pageable) {
        Page<UserProfileDto> usersProfile = userService.getAllUsersProfile(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "READ_USER_PROFILE_SUCCESS", "사용자 프로필 정보를 성공적으로 조회했습니다.", usersProfile));
    }
}
