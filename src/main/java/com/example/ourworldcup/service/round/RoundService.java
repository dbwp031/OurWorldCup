package com.example.ourworldcup.service.round;

import com.example.ourworldcup.controller.game.dto.RoundRequestDto;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.match.Round;

import java.util.List;

public interface RoundService {
    Round findById(Long id);

    public Round createRound(Game game, Long roundNum, Long item1Num, Long item2Num);
    void saveRound();


    void saveRoundResult(RoundRequestDto.RoundResultDto roundResultDto);

    Round getRound(Long gameId);

    void updateGameIfStageEnded(Long gameId);

    void incrementCurrentRoundOrder(Long gameId);

    void setCurrentRoundOrder(Long gameId, Long roundNum);

    Boolean checkFinished(Long gameId);

    List<Round> findAllRoundsByGameId(Long id);
}
