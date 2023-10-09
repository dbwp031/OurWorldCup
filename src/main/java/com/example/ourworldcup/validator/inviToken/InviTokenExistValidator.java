package com.example.ourworldcup.validator.inviToken;

import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.repository.InvitationRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InviTokenExistValidator implements ConstraintValidator<ExistInviToken, String> {
    private final InvitationRepository invitationRepository;
    private static InvitationRepository staticInvitationRepository;

    @PostConstruct
    public void init() {
        staticInvitationRepository = this.invitationRepository;
    }

    // ConstraintValidatorContext -> 유효성 검사 중에 에러메세지 등의 커스터마이징 필요할 때 사용
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!staticInvitationRepository.existsByUuid_Uuid(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.INVI_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }

    // custom validator 초기화 -> 어노테이션 속성 값을 검사기에 전달. 초기 설정
    // 어노테이션 타입의 매개변수가 제공
    @Override
    public void initialize(ExistInviToken constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
