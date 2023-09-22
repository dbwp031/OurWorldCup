package com.example.ourworldcup.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    //UserAccount
    USER_ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 이름의 역할이 없습니다"),

    ENTITY_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "해당 ENTITY를 DB에서 찾을 수 없습니다."),

    //Auth
    JWT_BAD_REQUEST(HttpStatus.UNAUTHORIZED, "잘못된 JWT입니다." ),
    JWT_ALL_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰들이 모두 만료되었습니다"),
    JWT_DENIED(HttpStatus.UNAUTHORIZED, "토큰 해독 중 발생하였습니다."),
    INVALID_BASIC_AUTH(HttpStatus.UNAUTHORIZED, "잘못된 userId입니다."),
    JWT_SERVER_ERROR(HttpStatus.UNAUTHORIZED, "jwt 에러가 발생했습니다."),
            ;
    
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
