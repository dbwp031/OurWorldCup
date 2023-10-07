package com.example.ourworldcup.exception.handler;

import com.example.ourworldcup.exception.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode.name());
    }
}
