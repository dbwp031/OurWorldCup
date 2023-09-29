package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Invitation;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.MemberRole;
import com.example.ourworldcup.domain.relation.Member;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.repository.MemberRepository;
import com.example.ourworldcup.repository.WorldcupRepository;
import com.example.ourworldcup.service.invitation.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/*
* 한 클래스 내의 다른 메서드를 호출할 때는 Spring의 프록시 기반 AOP 동작이 발생하지 않는다.
* createWorldcup 트랜잭션 생성 -> enrollUserAccount 트랜잭션X: 하나의 트랜잭션 안에서 모든 기능 수행
* 명시적으로 메서드가 새로운 트랜잭션을 시작하려고 하려면 -> @Transactional(propagation=Propagation.REQUIRES_NEW)를 사용하면 된다.
* */
@Transactional
@RequiredArgsConstructor
@Service
public class WorldcupServiceImpl implements WorldcupService{
    private final WorldcupRepository worldcupRepository;
    private final MemberRepository memberRepository;
    private final InvitationService invitationService;

    @Override
    public Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto, UserAccount userAccount) {
        Worldcup worldcup = Worldcup.builder()
                .title(worldcupCreateRequestDto.getTitle())
                .content(worldcupCreateRequestDto.getContent())
                .password(worldcupCreateRequestDto.getPassword())
                .build();
        Invitation invitation = invitationService.createInvitation(worldcup);
        worldcup.setInvitation(invitation);

        worldcupRepository.save(worldcup);
        enrollUserAccount(worldcup, userAccount, MemberRole.ADMIN);
        return worldcup;
    }
    @Override
    public void enrollUserAccount(Worldcup worldcup, UserAccount userAccount, MemberRole memberRole) {
        Member member = Member.builder()
                .worldcup(worldcup)
                .userAccount(userAccount)
                .memberRole(memberRole)
                .build();
        memberRepository.save(member);
    }

    /*
     * Worldcup worldcup = WorldcupRepository.getReferenceById(Long id):
     * 위 함수로 불러오면 worldcup 객체는 프록시 객체이다.
     * 프록시 객체는 실제 필요한 시점에 데이터베이스로부터 데이터를 로드하는 특성을 갖고 있다.
     * worldcup 객체가 실제로 사용되는 시점에만 데이터베이스에서 접근한다.
     *
     * 만약 트랜잭션 내부에서 객체를 사용해야 올바르게 불러와진다. 그렇지 않으면 LazyInitializationException이 발생한다.
     *
     * 일반적으로, 연관관계 설정을 위해서 많이 사용된다.
     * */

    @Override
    public Optional<Worldcup> findById(Long worldcupId) {
        return worldcupRepository.findById(worldcupId);
    }
}
