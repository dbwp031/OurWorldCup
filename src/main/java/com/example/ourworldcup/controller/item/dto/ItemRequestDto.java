package com.example.ourworldcup.controller.item.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


public class ItemRequestDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ItemCreateRequestDto {
        private String title;
        private MultipartFile image;
    }
}
