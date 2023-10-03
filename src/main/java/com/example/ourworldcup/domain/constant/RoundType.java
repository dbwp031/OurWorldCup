package com.example.ourworldcup.domain.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RoundType {
    ROUND1("결승",1L, 10L),
    ROUND4("준결승",2L, 9L),
    ROUND8("8강",4L, 8L),
    ROUND16("16강",8L,7L),
    ROUND32("32강",16L,6L);

    RoundType(String title, Long totalRounds, Long stageOrder) {
        this.title = title;
        this.totalRounds = totalRounds;
        this.stageOrder = stageOrder;
    }

    public static RoundType getRoundType(Long initialRound) {
        return Arrays.stream(RoundType.values())
                .filter(roundType -> roundType.totalRounds.equals(initialRound/2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 totalRound 수입니다."));
    }

    public static RoundType getNextRoundType(RoundType currentRoundType) {
        return Arrays.stream(RoundType.values())
                .filter(rt -> rt.getStageOrder().equals(currentRoundType.getStageOrder()+1))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알맞은 다음 RoundType이 존재하지 않습니다."));
    }
    private String title;
    private Long totalRounds;
    private Long stageOrder;
}
