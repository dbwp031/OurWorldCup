package com.example.ourworldcup.controller.invitation.dto;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import lombok.*;


public class InvitationResponseDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ValidityDto {
        private Long id;
        private String token;
        private WorldcupResponseDto.PreviewDto worldcup;
    }
}
