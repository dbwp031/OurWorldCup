package com.example.ourworldcup.converter.invitation;

import com.example.ourworldcup.controller.invitation.dto.InvitationResponseDto;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InvitationConverter {
    public static InvitationResponseDto.ValidityDto toInvitationResponseValidityDto(Invitation invitation) {
        return InvitationResponseDto.ValidityDto.builder()
                .id(invitation.getId())
                .token(invitation.getUuid().getUuid())
                .worldcup(WorldcupConverter.toWorldcupResponsePreviewDto(invitation.getWorldcup()))
                .build();
    }
}
