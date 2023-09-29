package com.example.ourworldcup.controller.worldcup.dto;

import com.example.ourworldcup.controller.userAccount.dto.UserAccountResponseDto;
import lombok.*;


public class MemberResponseDto {
    @Builder
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicDto {
        private Long id;
        private UserAccountResponseDto.BasicDto userAccountDto;
        private WorldcupResponseDto.BasicDto worldcupDto;
        private String memberRole;
    }
}
