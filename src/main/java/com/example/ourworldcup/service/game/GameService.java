package com.example.ourworldcup.service.game;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.dto.VsResultDto;

import java.util.List;

public interface GameService {
    Game createGame(Long userAccountId, Long worldcupId, Long initialRound, PickType pickType);

    Game findById(Long id);

    List<Round> initializeRounds(Worldcup worldcup, Game game, Long roundType, PickType pickType);

    Long getCurrentRoundOrderOnCurrentStage(Game game);

    Item getWinnerItem(Long gameId);

    List<Game> findGamesByWorldcupId(Long id);

    List<Game> findGamesByUserAccountId(Long id);
    List<VsResultDto> getVsResults(Long gameId);
}
