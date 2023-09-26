package com.example.ourworldcup.config;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import com.example.ourworldcup.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
/*
* JpaConfig -> 함수형 인터페이스: 하나의 추상 메서드만 가진 인터페이스
* 함수형 인터페이스에 대한 구현체를 빈 등록할 때에는 아래와 같이 람다함수를 써서 정의가 가능하다.
* */
@Slf4j
@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            return Optional.ofNullable(authentication.getPrincipalDetails().getUserName());
        };
    }
}
