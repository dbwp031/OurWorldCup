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
    /*
     * 잘못된 요청
     * */
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "COMMON001", "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "COMMON002", "권한이 잘못되었습니다."),
    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다. "),
    _FORBIDDEN(FORBIDDEN, "COMMON004", "금지된 요청입니다."),

    USER_ACCOUNT_NOT_FOUND(BAD_REQUEST, "USER_ACCOUNT_400_1", "사용자가 없습니다"),
    AUTHORITY_NOT_FOUND(BAD_REQUEST, "AUTHORITY_400_1", "권한 엔티티가 없습니다."),
    /*
     * 인증 관련 에러코드
     * */
    JWT_BAD_REQUEST(BAD_REQUEST, "AUTH001", "올바르지 않습니다. 다시 요청해주세요"),
    FORBIDDEN_EXCEPTION(UNAUTHORIZED, "AUTH002", "해당 요청에 대한 권한이 없습니다."),
    UNAUTHORIZED_EXCEPTION(UNAUTHORIZED, "AUTH003", "로그인 후 이용가능합니다. 토큰을 입력해 주세요"),
    EXPIRED_JWT_EXCEPTION(UNAUTHORIZED, "AUTH004", "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."),
    RELOGIN_EXCEPTION(UNAUTHORIZED, "AUTH005", "모든 토큰이 만료되었습니다. 다시 로그인해주세요."),
    INVALID_TOKEN_EXCEPTION(UNAUTHORIZED, "AUTH006", "토큰이 올바르지 않습니다."),
    HIJACK_JWT_TOKEN_EXCEPTION(UNAUTHORIZED, "AUTH007", "탈취된(로그아웃 된) 토큰입니다 다시 로그인 해주세요."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "AUTH009", "리프레쉬 토큰이 유효하지 않습니다. 다시 로그인 해주세요"),
    JWT_SERVER_ERROR(UNAUTHORIZED, "AUTH0010", "JWT 에러가 발생했습니다."),
    JWT_DENIED(BAD_REQUEST, "AUTH0011", "JWT 승인이 거부당했습니다"),
    /*
     * WORLDCUP 관련 에러코드
     * */
    WORLDCUP_NOT_FOUND(BAD_REQUEST, "WORLDCUP_400_1", "잘못된 월드컵 요청입니다."),

    /*
     * 초대 관련 에러코드
     * */
    INVI_NOT_FOUND(BAD_REQUEST, "INVITATION_400_1", "잘못된 초대 토큰입니다."),
    /*
     * Game 관련 에러코드
     * */
    SELECTION_DENIED(BAD_REQUEST, "GAME_400_1", "아이템 선택 기준을 충족하지 않습니다.");

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
