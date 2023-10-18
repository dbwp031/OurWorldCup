package com.example.ourworldcup.converter.round;

import com.example.ourworldcup.domain.constant.RoundType;

import java.util.Arrays;

public class RoundTypeConverter {
    public static RoundType toRoundType(Long initRound) {
        return Arrays.stream(RoundType.values())
                .filter(roundType -> roundType.getTotalRounds().equals(initRound))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 initRound값입니다."));
    }
}
