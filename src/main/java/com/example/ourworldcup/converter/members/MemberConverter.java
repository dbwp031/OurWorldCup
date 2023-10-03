package com.example.ourworldcup.converter.members;

import com.example.ourworldcup.controller.worldcup.dto.MemberResponseDto;
import com.example.ourworldcup.converter.userAccount.UserAccountConverter;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.relation.Member;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class MemberConverter {
    @PostConstruct
    public void init() {
    }

    public static MemberResponseDto.BasicDto toMemberResponseBasicDto(Member member) {
        return MemberResponseDto.BasicDto.builder()
                .id(member.getId())
                .worldcupDto(WorldcupConverter.toWorldcupResponseBasicDto(member.getWorldcup()))
                .userAccountDto(UserAccountConverter.toUserAccountResponseBasicDto(member.getUserAccount()))
                .memberRole(member.getMemberRole().name())
                .build();
    }
}
