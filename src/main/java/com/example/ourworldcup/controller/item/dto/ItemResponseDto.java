package com.example.ourworldcup.controller.item.dto;

import lombok.*;
import org.springframework.stereotype.Service;

public class ItemResponseDto {
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicDto {
        private Long id;
        private Long worldcupId;
        private String base64ItemImage;
        private String title;
    }
}
