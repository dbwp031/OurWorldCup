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
    public static class TextItemCreateRequestDto {
        private String title;
    }
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ImageItemsCreateRequestDto {
        private MultipartFile[] images;
    }
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ImageItemCreateRequestDto {
        private MultipartFile image;
    }
}
