package com.example.ourworldcup.repository.uuid;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.ourworldcup.domain.QUuid.uuid1;

@Hidden
@Repository
@RequiredArgsConstructor
public class UuidCustomRepositoryImpl implements UuidCustomRepository {
    private final EntityManager em;
    private final JPAQueryFactory factory;

    /*
     * JpaRepository:
     * 간결성, 표준화, 매직 메서드
     * Querydsl:
     * 타입 세이프, 동적 쿼리, 코드 자동완성, 복잡한 쿼리
     * */

    // .fetchFirst() -> LIMIT 걸어줌.
    @Override
    public Boolean exist(String uuid) {
        Integer fetchOne = factory.selectOne()
                .from(uuid1)
                .where(uuid1.uuid.eq(uuid))
                .fetchFirst();
        return fetchOne != null;
    }
}
