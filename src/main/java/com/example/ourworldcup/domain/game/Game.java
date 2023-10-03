package com.example.ourworldcup.domain.game;

import com.example.ourworldcup.domain.AuditingFields;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.GameType;
import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Game extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @Builder.Default
    @OneToMany
    private List<Round> rounds = new ArrayList<>();

    @ManyToOne
    private Worldcup worldcup;

    @ManyToOne
    private UserAccount player;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @Enumerated(EnumType.STRING)
    private PickType pickType;

    @Enumerated(EnumType.STRING)
    private RoundType initialRoundType;

    @Enumerated(EnumType.STRING)
    private RoundType currentRoundType;

    private Long currentRoundOrder;

    private Long nextStageEndRoundOrder;
    public void addRound(Round round) {
        rounds.add(round);
    }

    public void addRounds(List<Round> rounds) {
        this.rounds.addAll(rounds);
    }

    public void setCurrentRoundType(RoundType currentRoundType) {
        this.currentRoundType = currentRoundType;
    }

    public void setNextStageEndRoundOrder(Long nextStageEndRoundOrder) {
        this.nextStageEndRoundOrder = nextStageEndRoundOrder;
    }

    public void incrementCurrentRoundOrder() {
        this.currentRoundOrder +=1L;
    }

    public void setCurrentRoundOrder(Long roundOrder) {
        this.currentRoundOrder = roundOrder;
    }
}
