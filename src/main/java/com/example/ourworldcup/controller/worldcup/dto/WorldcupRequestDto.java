package com.example.ourworldcup.controller.worldcup.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class WorldcupRequestDto {
    @Builder
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorldcupCreateRequestDto {
        private String title;
        private String contents;
        private String password;
    }

}
