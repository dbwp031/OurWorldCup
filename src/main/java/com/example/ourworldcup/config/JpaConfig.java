package com.example.ourworldcup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("수정자"); //TODO: 현재는 지금 수정중인 유저의 정보를 알 수 있는 방법이 없다. 이후 Spring Security로 보안 설정시 가동할 수 있도록 할 것.
    }
}
