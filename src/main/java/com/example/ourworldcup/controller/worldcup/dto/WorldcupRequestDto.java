package com.example.ourworldcup.controller.worldcup.dto;

import lombok.*;
import org.springframework.stereotype.Service;

public class WorldcupRequestDto {
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorldcupCreateRequestDto {
        private String title;
        private String content;
        private String password;
    }

}
