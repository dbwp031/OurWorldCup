package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.relation.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/*
* @Modifying은 @Query와 함께 사용되며, 해당 쿼리가 데이터베이스의 내용을 바꾸는 DML임을 나타냅니다. 이게 없으면 @Query가 올바르게 동작하지 않습니다.
* */
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Modifying
    @Query("DELETE FROM Member m WHERE m.id = :id")
    void deleteByIdDirectly(@Param("id") Long id);
}
