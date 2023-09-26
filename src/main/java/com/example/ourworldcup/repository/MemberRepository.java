package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.relation.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
