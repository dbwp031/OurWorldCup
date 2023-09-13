package com.example.ourworldcup.controller.worldcup.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class WorldcupResponseDto {

    @Builder
    @Setter
    @Getter
    @Service
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorldcupItemsDto {
        // title, base64Image
        @Builder.Default private Map<String, String> items = new LinkedHashMap<>();

    }
}
