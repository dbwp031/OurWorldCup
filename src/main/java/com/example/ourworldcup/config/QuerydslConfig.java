package com.example.ourworldcup.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class QuerydslConfig {

    private final EntityManager em;
    
    /*
    * JPA를 기반으로 동작하는 타입 세이프한 쿼리를 생성, 실행하는데 사용
    * 1) 타입 세이프
    * 2) 코드 자동 완성
    * 3) 동적 쿼리
     * */
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
