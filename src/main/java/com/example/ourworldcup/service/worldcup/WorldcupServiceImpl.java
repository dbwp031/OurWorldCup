package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.aws.s3.AmazonS3Service;
import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.MemberRole;
import com.example.ourworldcup.domain.relation.Member;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.repository.MemberRepository;
import com.example.ourworldcup.repository.WorldcupRepository;
import com.example.ourworldcup.repository.item.ItemRepository;
import com.example.ourworldcup.service.item.ItemImageService;
import com.example.ourworldcup.service.item.impl.ItemImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.World;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Override
    public Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto, UserAccount userAccount) {
        // TODO: invitationCode 생성 및 저장 수정해야함.
        Worldcup worldcup = Worldcup.builder()
                .title(worldcupCreateRequestDto.getTitle())
                .content(worldcupCreateRequestDto.getContent())
                .password(worldcupCreateRequestDto.getPassword())
                .invitationCode("temp")
                .build();
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
}
