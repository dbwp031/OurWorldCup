package com.example.ourworldcup.converter.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Worldcup;

public class WorldcupConverter {
    public static WorldcupResponseDto.BasicDto toWorldcupResponseBasicDto(Worldcup worldcup) {
        return WorldcupResponseDto.BasicDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .invitationCode(worldcup.getInvitationCode())
                .password(worldcup.getPassword())
                .build();
    }
}
