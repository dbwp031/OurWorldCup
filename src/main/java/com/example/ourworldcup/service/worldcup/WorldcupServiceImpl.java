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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/*
 * 한 클래스 내의 다른 메서드를 호출할 때는 Spring의 프록시 기반 AOP 동작이 발생하지 않는다.
 * createWorldcup 트랜잭션 생성 -> enrollUserAccount 트랜잭션X: 하나의 트랜잭션 안에서 모든 기능 수행
 * 명시적으로 메서드가 새로운 트랜잭션을 시작하려고 하려면 -> @Transactional(propagation=Propagation.REQUIRES_NEW)를 사용하면 된다.
 * */
@Transactional
@RequiredArgsConstructor
@Service
public class WorldcupServiceImpl implements WorldcupService {
    private final WorldcupRepository worldcupRepository;
    private final MemberRepository memberRepository;
    private final InvitationService invitationService;

    @PersistenceContext
    EntityManager em;
    private final static Integer MINIMUM_ROUND_TYPE = 4;
    private final static Integer MAXIMUM_ROUND_TYPE = 64;

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
    public Worldcup findByInvitation(Invitation invitation) {
        return worldcupRepository.findByInvitation_Uuid_Uuid(invitation.getUuid().getUuid())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 월드컵이 없습니다."));

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

    @Override
    public void enrollUserAccount(Invitation invitation, UserAccount userAccount, MemberRole memberRole) {
        Worldcup worldcup = invitation.getWorldcup();
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
    public Worldcup findById(Long worldcupId) {
        return worldcupRepository.findById(worldcupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 worldcupId가 없습니다."));
    }

    @Override
    public List<Integer> getRoundTypes(Long id) {
        em.flush();
        Worldcup worldcup = this.findById(id);
        Integer itemsSize = worldcup.getItems().size();
        System.out.println("itemsSize: " + itemsSize);
        System.out.println(worldcup);
        if (itemsSize < MINIMUM_ROUND_TYPE) {
            throw new IllegalStateException("The number of items in the Worldcup is less than the minimum required.");
        }
        List<Integer> roundTypes = new ArrayList<>();
        Integer dist = MINIMUM_ROUND_TYPE;
        for (Integer roundType = MINIMUM_ROUND_TYPE; roundType <= MAXIMUM_ROUND_TYPE; roundType += dist) {
            if (roundType > itemsSize) {
                break;
            }
            roundTypes.add(roundType);
            dist = roundType;
        }
        return roundTypes;
    }

    @Override
    public String getTitle(Long id) {
        Worldcup worldcup = this.findById(id);
        return worldcup.getTitle();
    }
}
