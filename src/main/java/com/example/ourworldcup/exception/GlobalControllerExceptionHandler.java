package com.example.ourworldcup.exception;

import com.example.ourworldcup.exception.common.ApiErrorResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResult> constraintViolationExceptionHandler(ConstraintViolationException e) {
        ConstraintViolation constraintViolation = e.getConstraintViolations().stream().toList().get(0);
        String errorCodeString = constraintViolation.getMessageTemplate();
        ErrorCode errorCode = ErrorCode.valueOf(errorCodeString);
        String cause = e.getClass().getName();
        return ResponseEntity.badRequest()
                .body(ApiErrorResult.builder()
                        .errorCode(errorCode)
                        .message(errorCode.getMessage())
                        .cause(cause)
                        .build());
    }
}
