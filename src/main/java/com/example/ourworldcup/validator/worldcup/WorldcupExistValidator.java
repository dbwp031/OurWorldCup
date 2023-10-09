package com.example.ourworldcup.validator.worldcup;

import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.repository.WorldcupRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WorldcupExistValidator implements ConstraintValidator<ExistWorldcup, Long> {
    private final WorldcupRepository worldcupRepository;
    private static WorldcupRepository staticWorldcupRepository;

    @PostConstruct
    public void init() {
        staticWorldcupRepository = this.worldcupRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (!staticWorldcupRepository.existsById(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.WORLDCUP_NOT_FOUND.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    public void initialize(ExistWorldcup constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
