package com.example.ourworldcup.controller.worldcup.dto;

import com.example.ourworldcup.domain.relation.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WorldcupResponseDto {

    @Builder
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicDto {
        private Long id;
        private String title;
        private String content;
        private String invitationCode;
        private String password;
    }
    @Builder
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MembersDto {
        private Long id;
        private String title;
        private String content;
        private List<MemberResponseDto.BasicDto> members;
    }


        @Builder
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorldcupItemDto {
        private Long id;
        private String title;
        private String base64Image;
    }
    @Builder
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvitationDto {
        private Long id;
        private String title;
        private String content;
    }
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorldcupItemsDto {
        @Builder.Default
        private List<WorldcupItemDto> items = new ArrayList<>();
    }
}
