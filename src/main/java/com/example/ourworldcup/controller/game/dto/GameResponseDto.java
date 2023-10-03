package com.example.ourworldcup.controller.game.dto;

import com.example.ourworldcup.controller.item.dto.ItemResponseDto;
import com.example.ourworldcup.controller.userAccount.dto.UserAccountResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.constant.GameType;
import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.domain.match.Round;
import lombok.*;

import java.util.List;

public class GameResponseDto {
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoundInfoDto {
        private Long id;
        private String title;
        private String currentRoundTypeTitle;
        private Long stageTotalRound;
        private Long currentRoundOrder;
    }
    @Builder
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultDto {
        private Long id;
        private WorldcupResponseDto.BasicDto worldcup;
        private UserAccountResponseDto.BasicDto player;
        private GameType gameType;
        private PickType pickType;
        private RoundType initialRoundType;
        private List<RoundResponseDto.BasicDto> rounds;
    }

}
