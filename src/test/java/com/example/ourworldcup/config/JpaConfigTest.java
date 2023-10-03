package com.example.ourworldcup.config;

import com.example.ourworldcup.domain.Worldcup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;


/*
* @DataJpaTest
* - 슬라이스 테스트: Spring Boot가 전체 컨텍스트 로드 대신 JPA 관련 빈들만 로드하여 빠른 테스트가 가능하게 하낟.
* - Jpa에 관련된 요소들만 테스트하기 위한 어노테이션 / JPA 테스트에 관련된 설정들만 적용
* - 메모리 상에 DB 생성, 엔티티 등록 및 Repository 설정
* - 각 테스트마다 테스트가 완료되면 관련 설정 롤백
* - TestEntityManager를 제공
* ref: https://twpower.github.io/293-test-jpa-repository-using-data-jpa-test
*
* @WithMockUser
* - SpringSecurity의 테스트 지원 기능을 통해 특정 사용자를 모의 인증 상태로 만든다.
* */

@Disabled("엔티티 수정에 따른 테스트 임시 비활성화")
@DataJpaTest
@DisplayName("JpaConfig - AuditingField 생성 확인")
@ActiveProfiles(profiles = {"test"})
@Import({QuerydslConfig.class, JpaConfig.class})
class JpaConfigTest {
    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("[jpa] - 월드컵 생성시, createdBy가 올바르게 등록")
    @Test
    @WithMockUser(username = "testUser")
    public void givenMockUser_whenSaveWorldcup_thenSaveCreatedByOfWorldcupAsMockUser() {
        Worldcup worldcup = Worldcup.builder()
                .title("test")
                .password("test")
//                .invitationCode("test")
                .build();
        Worldcup savedWorldcup = entityManager.persistAndFlush(worldcup);

        Assertions.assertThat(savedWorldcup.getCreatedBy()).isEqualTo("testUser");
    }
}