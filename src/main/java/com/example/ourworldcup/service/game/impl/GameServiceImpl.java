package com.example.ourworldcup.service.game.impl;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.GameType;
import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.dto.VsResultDto;
import com.example.ourworldcup.repository.GameRepository;
import com.example.ourworldcup.repository.RoundRepository;
import com.example.ourworldcup.repository.UserAccountRepository;
import com.example.ourworldcup.service.game.GameService;
import com.example.ourworldcup.service.item.ItemService;
import com.example.ourworldcup.service.userAccount.UserAccountService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {
    private final RoundRepository roundRepository;
    private final UserAccountRepository userAccountRepository;
    private final GameRepository gameRepository;

    private final UserAccountService userAccountService;
    private final ItemService itemService;
    private final WorldcupService worldcupService;

    @Transactional
    @Override
    public Game createGame(Long userAccountId, Long worldcupId, Long initialRound, PickType pickType) {

        if (!worldcupService.getRoundTypes(worldcupId).contains(initialRound.intValue())) {
            throw new IllegalArgumentException("해당 initialRound는 올바르지 않습니다.");
        }

        UserAccount userAccount = userAccountService.findById(userAccountId);
        Worldcup worldcup = worldcupService.findById(worldcupId);

        RoundType initialRoundType = RoundType.getRoundType(initialRound);
        Game game = Game.builder()
                .worldcup(worldcup)
                .player(userAccount)
                .gameType(GameType.TOURNAMENT)
                .pickType(pickType)
                .initialRoundType(initialRoundType)
                .currentRoundType(initialRoundType)
                .currentRoundOrder(0L)
                .nextStageEndRoundOrder(initialRoundType.getTotalRounds())
                .build();

        List<Round> initializeRounds = this.initializeRounds(worldcup, game, initialRound, pickType);
        game.addRounds(initializeRounds);
        gameRepository.save(game);
        return game;
    }

    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Game 아이디가 존재하지 않습니다."));
    }

    @Transactional
    @Override
    public List<Round> initializeRounds(Worldcup worldcup, Game game, Long roundType, PickType pickType) {
        List<Item> items = worldcup.getItems();
        List<Integer> pickForm = pickType.getPickOrder(roundType);
        List<Round> initialRounds = new ArrayList<>();
        for (int i = 0; i < pickForm.size(); i += 2) {
            Round round = Round.builder()
                    .worldcup(worldcup)
                    .game(game)
                    .item1(items.get(pickForm.get(i)).getId())
                    .item2(items.get(pickForm.get(i + 1)).getId())
                    .roundNum((long) i / 2)
                    .build();
            roundRepository.save(round);
            initialRounds.add(round);
        }
        return initialRounds;
    }

    @Override
    public Long getCurrentRoundOrderOnCurrentStage(Game game) {
        AtomicReference<Long> roundSum = new AtomicReference<>((long) 0);
        Arrays.stream(RoundType.values()).forEach(
                r -> {
                    if (r.getStageOrder() >= game.getInitialRoundType().getStageOrder() && r.getStageOrder() < game.getCurrentRoundType().getStageOrder()) {
                        roundSum.updateAndGet(v -> v + r.getTotalRounds());
                    }
                }
        );
        return game.getCurrentRoundOrder() - roundSum.get();
    }

    @Override
    public Item getWinnerItem(Long gameId) {
        Game game = this.findById(gameId);
        return itemService.findById(game.getRounds().get(game.getRounds().size() - 1).getSelectedItem());
    }

    @Override
    public List<Game> findGamesByWorldcupId(Long id) {
        return gameRepository.findAllByWorldcupId(id);
    }

    @Override
    public List<Game> findGamesByUserAccountId(Long userAccountId) {
        return gameRepository.findAllByPlayer_id(userAccountId);
    }

    @Override
    public List<VsResultDto> getVsResults(Long gameId) {
        Game source = this.findById(gameId);
        List<Game> games = gameRepository.findAll().stream()
                .filter(g -> g.getCurrentRoundType().equals(RoundType.ROUND2) && g.getCurrentRoundOrder().equals(g.getNextStageEndRoundOrder()))
                .filter(g -> !Objects.equals(g.getId(), gameId))
                .toList();
        List<VsResultDto> vsResultDtos = new ArrayList<>();
        for (Game game : games) {
            VsResultDto vsResultDto = VsResultDto.builder()
                    .sourceGameId(gameId)
                    .targetGameId(game.getId())
                    .targetPlayerName(game.getPlayer().getUserName())
                    .score(getSimilarScore(source, game)).build();
            vsResultDtos.add(vsResultDto);
        }
        return vsResultDtos;
    }

    public Long getSimilarScore(Game source, Game target) {
        if (!source.getInitialRoundType().equals(target.getInitialRoundType())) {
            return 0L;
        }
        long[] stageSplitIndexes = null;
        long[] stagePoint = null;
        if (source.getInitialRoundType().equals(RoundType.ROUND16)) {
            stageSplitIndexes = new long[]{7, 11, 13, 14};
            stagePoint = new long[]{1, 2, 3, 4};
        } else if (source.getInitialRoundType().equals(RoundType.ROUND8)) {
            stageSplitIndexes = new long[]{4, 6, 7};
            stagePoint = new long[]{1, 2, 3};
        } else if (source.getInitialRoundType().equals(RoundType.ROUND4)) {
            stageSplitIndexes = new long[]{2, 3};
            stagePoint = new long[]{1, 2};
        } else {
            throw new RuntimeException("16강 이후의 케이스는 아직 구현하지 않았습니다.");
        }
        int score = 0;
        int index = 0;
        int currentStageSplitIndex = 0;
        List<Round> sourceRounds = source.getRounds();
        List<Round> targetRounds = target.getRounds();

        while (currentStageSplitIndex < stageSplitIndexes.length) {
            List<Long> sourceSelectedItem = new ArrayList<>();
            List<Long> targetSelectedItem = new ArrayList<>();
            while (index < stageSplitIndexes[currentStageSplitIndex]) {
                sourceSelectedItem.add(sourceRounds.get(index).getSelectedItem());
                targetSelectedItem.add(targetRounds.get(index).getSelectedItem());
                index += 1;
            }
            for (Long s : sourceSelectedItem) {
                if (targetSelectedItem.contains(s)) {
                    score += stagePoint[currentStageSplitIndex];
                }
            }
            currentStageSplitIndex += 1;
        }
        return (long) score;
    }


}
