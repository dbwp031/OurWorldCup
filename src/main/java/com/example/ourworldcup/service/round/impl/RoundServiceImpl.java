package com.example.ourworldcup.service.round.impl;

import com.example.ourworldcup.controller.game.dto.RoundRequestDto;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.repository.RoundRepository;
import com.example.ourworldcup.service.game.GameService;
import com.example.ourworldcup.service.round.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;
    private final GameService gameService;

    @Override
    public Round findById(Long id) {
        return roundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 라운드가 존재하지 않습니다."));

    }

    @Override
    public Round createRound(Game game, Long roundNum, Long item1Num, Long item2Num) {
        return Round.builder()
                .worldcup(game.getWorldcup())
                .game(game)
                .roundNum(roundNum)
                .item1(item1Num)
                .item2(item2Num)
                .build();
    }

    @Override
    public void saveRound() {

    }

    @Override
    public void saveRoundResult(RoundRequestDto.RoundResultDto roundResultDto) {
        Round round = this.findById(roundResultDto.getRoundId());
        round.setSelectedItem(roundResultDto.getSelectedItem());
    }

    @Override
    public Round getRound(Long gameId) {
        Game game = gameService.findById(gameId);
        return game.getRounds().get(game.getCurrentRoundOrder().intValue());
    }

    public List<Round> createNewStageRoundsOnlyIfStageEnded(Game game) {
        Long roundNumIdx = 0L;
        List<Round> newRounds = new ArrayList<>();
        for (long i = game.getCurrentRoundOrder() - game.getCurrentRoundType().getTotalRounds(); i < game.getCurrentRoundOrder(); i += 2) {
            Long newItem1Num = game.getRounds().get((int) i).getSelectedItem();
            Long newItem2Num = game.getRounds().get((int) i + 1).getSelectedItem();
            Round newRound = Round.builder()
                    .worldcup(game.getWorldcup())
                    .game(game)
                    .roundNum(game.getCurrentRoundOrder() + roundNumIdx)
                    .item1(newItem1Num)
                    .item2(newItem2Num)
                    .build();
            roundNumIdx += 1L;
            roundRepository.save(newRound);
            newRounds.add(newRound);
        }
        return newRounds;
    }

    @Override
    public void updateGameIfStageEnded(Long gameId) {
        Game game = gameService.findById(gameId);
        if (game.getCurrentRoundOrder().equals(game.getNextStageEndRoundOrder())) {
            game.addRounds(createNewStageRoundsOnlyIfStageEnded(game));
            game.setCurrentRoundType(RoundType.getNextRoundType(game.getCurrentRoundType()));
            game.setNextStageEndRoundOrder(game.getNextStageEndRoundOrder() + game.getCurrentRoundType().getTotalRounds());
            return;
        }
        return;
    }

    @Override
    public void incrementCurrentRoundOrder(Long gameId) {
        Game game = gameService.findById(gameId);
        game.incrementCurrentRoundOrder();
    }

    @Override
    public void setCurrentRoundOrder(Long gameId, Long roundOrder) {
        Game game = gameService.findById(gameId);
        game.setCurrentRoundOrder(roundOrder);
    }

    @Override
    public Boolean checkFinished(Long gameId) {
        Game game = gameService.findById(gameId);
        return game.getCurrentRoundType().equals(RoundType.ROUND2);
    }

    @Override
    public List<Round> findAllRoundsByGameId(Long id) {
        return roundRepository.findAllByGameId(id);
    }
}
