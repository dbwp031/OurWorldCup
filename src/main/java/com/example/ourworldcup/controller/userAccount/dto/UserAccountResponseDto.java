package com.example.ourworldcup.controller.userAccount.dto;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import lombok.*;

import java.util.List;

public class UserAccountResponseDto {
    @Builder
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicDto {
        private Long id;
        private String userId;
        private String userName;
        private String nickName;
        private String email;
        private String picture;
    }
    @Builder
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WithWorldcupsDto {
        private Long id;
        private String userId;
        private String userName;
        private String nickName;
        private String email;
        private String picture;
        private List<WorldcupResponseDto.BasicDto> worldcups;
    }

}
