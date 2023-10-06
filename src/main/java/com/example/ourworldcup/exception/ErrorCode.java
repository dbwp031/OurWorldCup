package com.example.ourworldcup.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static org.springframework.http.HttpStatus.*;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "COMMON001", "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "COMMON002", "권한이 잘못되었습니다."),
    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다. "),
    _FORBIDDEN(FORBIDDEN, "COMMON004", "금지된 요청입니다."),

    USER_ACCOUNT_NOT_FOUND(BAD_REQUEST, "USER_ACCOUNT_400_1", "사용자가 없습니다"),

    // 인증 관련 에러
    JWT_BAD_REQUEST(BAD_REQUEST, "AUTH001", "올바르지 않습니다. 다시 요청해주세요"),
    JWT_SERVER_ERROR(UNAUTHORIZED, "AUTH002","JWT 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static ErrorCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return ErrorCode._BAD_REQUEST;
                    } else {
                        return ErrorCode._INTERNAL_SERVER_ERROR;
                    }
                });
    }
    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}
