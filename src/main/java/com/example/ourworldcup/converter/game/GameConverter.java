package com.example.ourworldcup.converter.game;

import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.game.dto.GameResponseDto;
import com.example.ourworldcup.controller.game.dto.RoundRequestDto;
import com.example.ourworldcup.controller.game.dto.RoundResponseDto;
import com.example.ourworldcup.converter.round.RoundConverter;
import com.example.ourworldcup.converter.userAccount.UserAccountConverter;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.service.game.GameService;
import com.example.ourworldcup.service.round.RoundService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GameConverter {
    private final GameService gameService;
    private final RoundService roundService;
    private static GameService staticGameService;
    private static RoundService staticRoundService;

    @PostConstruct
    public void init() {
        staticGameService = gameService;
        staticRoundService = roundService;
    }

    public static GameResponseDto.RoundInfoDto toGameResponseRoundInfoDto(Game game) {
        return GameResponseDto.RoundInfoDto.builder()
                .id(game.getId())
                .title(game.getWorldcup().getTitle())
                .currentRoundTypeTitle(game.getCurrentRoundType().getTitle())
                .stageTotalRound(game.getCurrentRoundType().getTotalRounds())
                .currentRoundOrder(staticGameService.getCurrentRoundOrderOnCurrentStage(game))
                .build();
    }

    public static GameResponseDto.ResultDto toGameResponseResultDto(Game game) {
        List<RoundResponseDto.BasicDto> rounds = staticRoundService.findAllRoundsByGameId(game.getId())
                .stream().map(r -> {
                    try {
                        return RoundConverter.toRoundResponseBasicDto(r);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        return GameResponseDto.ResultDto.builder()
                .id(game.getId())
                .worldcup(WorldcupConverter.toWorldcupResponseBasicDto(game.getWorldcup()))
                .player(UserAccountConverter.toUserAccountResponseBasicDto(game.getPlayer()))
                .gameType(game.getGameType())
                .pickType(game.getPickType())
                .initialRoundType(game.getInitialRoundType())
                .rounds(rounds)
                .build();
    }
}
