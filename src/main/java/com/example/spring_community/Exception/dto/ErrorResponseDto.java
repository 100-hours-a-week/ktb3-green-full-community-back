package com.example.spring_community.Exception.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto extends ResponseDto{
    public ErrorResponseDto(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}
