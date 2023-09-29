package com.example.ourworldcup.converter.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.MemberResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.members.MemberConverter;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.service.invitation.InvitationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WorldcupConverter {
    private final InvitationService invitationService;
    private static InvitationService staticInvitationService;
    @PostConstruct
    public void init() {
        this.staticInvitationService = invitationService;
    }

    public static WorldcupResponseDto.BasicDto toWorldcupResponseBasicDto(Worldcup worldcup) {
        String invitationCode = staticInvitationService.getInvitationUrl(worldcup.getInvitation());

        return WorldcupResponseDto.BasicDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .invitationCode(invitationCode)
                .password(worldcup.getPassword())
                .build();
    }

    public static WorldcupResponseDto.InvitationDto toWorldcupResponseInvitationDto(Worldcup worldcup) {
        return WorldcupResponseDto.InvitationDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .build();
    }

    public static WorldcupResponseDto.MembersDto toWorldcupResponseMembersDto(Worldcup worldcup) {
        List<MemberResponseDto.BasicDto> members = worldcup.getMembers().stream()
                .map(MemberConverter::toMemberResponseBasicDto)
                .toList();
        return WorldcupResponseDto.MembersDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .members(members)
                .build();
    }
}
