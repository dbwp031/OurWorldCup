package com.example.ourworldcup.controller.game.dto;

import com.example.ourworldcup.controller.item.dto.ItemResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import lombok.*;
import org.springframework.stereotype.Service;

public class RoundRequestDto {
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoundResultDto {
        private Long roundId;
        private Long roundNum;
        private Long selectedItem;
    }
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicDto {
        private Long id;
        private Long worldcupId;
        private Long gameId;
        private Long roundNum;
        private ItemResponseDto.BasicDto item1;
        private ItemResponseDto.BasicDto item2;
        private ItemResponseDto.BasicDto selectedItem;
    }

}
