package com.example.spring_community.User.dto;

import com.example.spring_community.User.domain.UserEntity;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateUserDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 주소 형식을 입력해주세요.")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{8,20}$",
            message = "비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다.")
    private String password;

    @NotBlank(message="비밀번호를 한 번 더 입력해주세요.")
    private String checkPassword;

    @NotBlank(message="닉네임을 입력해주세요.")
    @Size(max = 10, message = "닉네임은 최대 10자까지 작성 가능합니다.")
    private String nickname;

    @NotNull(message = "프로필 사진을 추가해주세요.")
    private MultipartFile profileImg;

    @Builder
    public CreateUserDto(String email, String password, String checkPassword, String nickname, MultipartFile profileImg) {
        this.email = email;
        this.password = password;
        this.checkPassword = checkPassword;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

}
