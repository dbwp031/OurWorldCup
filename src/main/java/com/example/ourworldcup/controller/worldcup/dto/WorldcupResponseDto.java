package com.example.ourworldcup.controller.worldcup.dto;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.util.ItemUtil;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class WorldcupResponseDto {
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
