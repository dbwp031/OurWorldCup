package com.example.ourworldcup.repository.item;

import com.example.ourworldcup.domain.QItem;
import com.example.ourworldcup.domain.QWorldcup;
import com.example.ourworldcup.domain.Worldcup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository{
    private final EntityManager em;
    private final JPAQueryFactory factory;

    /*JOIN 종류:
     *   INNER JOIN: 두 테이블 간에 일치하는 행만 반환 (두 테이블간의 교집합)
     *   LEFT (OUTER) JOIN: 왼쪽 테이블의 모든 행 + 오른쪽 테이블 일치하는 행
     *   RIGHT (OUTER) JOIN: 오른쪽 테이블의 모든 행 + 왼쪽 테이블의 일차흔 행
     *   FULL OUTER JOIN: 두 테이블 사이의 모든 행
     *   SELF JOIN: 동일한 테이블을 두 번 이상 사용하여 테이블 내에서 관계를 찾는데 사용됨.
     * */
    @Override
    public boolean checkExistByItemTitleInSameWorldcup(Long worldcupId, String title) {
        QItem qItem = QItem.item;
        QWorldcup qWorldcup = QWorldcup.worldcup;

        // worldcup을 찾고,
        Integer fetchOne = factory.selectOne()
                .from(qItem)
                .where(qItem.worldcup.id.eq(worldcupId)
                        .and(qItem.title.eq(title)))
                .fetchFirst();

        return fetchOne != null;
    }
}
