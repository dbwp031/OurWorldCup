package com.example.ourworldcup.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {

    ENTITY_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "해당 ENTITY를 DB에서 찾을 수 없습니다.");
    
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
