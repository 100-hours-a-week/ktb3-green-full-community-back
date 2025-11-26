package com.example.spring_community.Exception.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResponseDto<T> extends ResponseDto{
    private final T data;

    private DataResponseDto(HttpStatus status, String code, String message, T data) {
        super(status, code, message);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(HttpStatus status, String code, String message, T data) {
        return new DataResponseDto<>(status, code, message, data);
    }


}
