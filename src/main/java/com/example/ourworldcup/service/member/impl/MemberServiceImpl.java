package com.example.ourworldcup.service.member.impl;

import com.example.ourworldcup.domain.relation.Member;
import com.example.ourworldcup.repository.MemberRepository;
import com.example.ourworldcup.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    /*
    * 연관관계가 모두 제거되지 않으면 delete가 동작하지 않는다.
    *  - 단, cascade 상황을 제외했을 때이다.
    *  - 주인쪽에서 연관관계를 다 끊어주어야 한다.
    *
    * */
    @Override
    public void deleteMemberById(Long id) {
        memberRepository.deleteByIdDirectly(id);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 member id 입니다"));
    }
}
