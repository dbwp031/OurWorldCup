package com.example.ourworldcup.validator.inviToken;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented // API 문서에 포함
@Constraint(validatedBy = InviTokenExistValidator.class) // 검증 어노테이션.
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistInviToken {
    // 유효성 검사 실패 시 반환되는 에러 메세지 정의
    String message() default "해당하는 초대 토큰이 없습니다.";

    // 검증 그룹 정의. 특정 상황에 따라 다른 유효성 검사 규칙을 적용할 수 있다.
    Class<?>[] groups() default {};

    // 유효성 검사 실패 시 추가적인 정보나 메타데이터를 연결하기 위한 속성
    Class<? extends Payload>[] payload() default {};
}
