package com.example.spring_community.Exception.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto {
    private final int status;
    private final String code;
    private final String message;

    public ResponseDto(HttpStatus status, String code, String message) {
        this.status = status.value();
        this.code = code;
        this.message = message;
    }

    public static ResponseDto of(HttpStatus status, String code, String message) {
        return new ResponseDto(status, code, message);
    }
}
