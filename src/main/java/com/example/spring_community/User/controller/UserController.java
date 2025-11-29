package com.example.spring_community.User.controller;

import com.example.spring_community.Auth.dto.CustomUserDetails;
import com.example.spring_community.Exception.dto.DataResponseDto;
import com.example.spring_community.Exception.dto.ResponseDto;
import com.example.spring_community.User.dto.CreateUserDto;
import com.example.spring_community.User.dto.UpdateUserProfileDto;
import com.example.spring_community.User.dto.UpdateUserPwDto;
import com.example.spring_community.User.dto.UserProfileDto;
import com.example.spring_community.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name="User API", description = "User 리소스에 관한 API 입니다.")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원가입", description = "입력받은 사용자 정보로 회원 가입을 진행합니다.")
    public ResponseEntity<DataResponseDto<UserProfileDto>> signup(@ModelAttribute @Valid CreateUserDto createUserDto) {
        UserProfileDto userProfileDto = userService.signup(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponseDto.of(HttpStatus.CREATED, "CREATED", "회원가입에 성공했습니다", userProfileDto));
    }

    @PatchMapping("/active")
    @Operation(summary = "회원탈퇴", description = "soft delete를 통한 회원 탈퇴를 진행합니다.")
    public ResponseEntity<ResponseDto> withdraw(@AuthenticationPrincipal CustomUserDetails principal) {
        userService.withdrawUser(principal.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, "WITHDRAW_USER_SUCCESS", "정상적으로 탈퇴 처리 되었습니다."));
    }

    @GetMapping("/profile")
    @Operation(summary = "사용자 프로필 조회", description = "사용자의 프로필 정보를 조회합니다")
    public ResponseEntity<DataResponseDto<UserProfileDto>> getUserProfile(@AuthenticationPrincipal CustomUserDetails principal) {
        UserProfileDto userProfileDto = userService.getUserProfile(principal.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "READ_USER_PROFILE_SUCCESS", "사용자 프로필 정보를 성공적으로 조회했습니다.", userProfileDto));
    }

    @PatchMapping("/profile")
    @Operation(summary = "사용자 프로필 수정", description = "닉네임과 프로필 이미지를 변경합니다.")
    public ResponseEntity<DataResponseDto<UserProfileDto>> updateUserProfile(@ModelAttribute UpdateUserProfileDto updateUserProfileDto, @AuthenticationPrincipal CustomUserDetails principal) {
        UserProfileDto updatedUser = userService.updateUserProfile(principal.getUserId(), updateUserProfileDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "UPDATE_PROFILE_SUCCESS", "프로필을 성공적으로 수정했습니다.", updatedUser));
    }

    @PatchMapping("/password")
    @Operation(summary = "사용자 비밀번호 수정", description = "비밀번호를 수정합니다. 이전과 동일한 비밀번호로는 수정할 수 없습니다.")
    public ResponseEntity<ResponseDto> updateUserPw(@RequestBody UpdateUserPwDto updateUserPwDto, @AuthenticationPrincipal CustomUserDetails principal) {
        userService.updateUserPw(principal.getUserId(), updateUserPwDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, "UPDATE_PASSWORD_SUCCESS", "정상적으로 비밀번호를 수정했습니다."));
    }
}
