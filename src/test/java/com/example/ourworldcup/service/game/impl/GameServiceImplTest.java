package com.example.ourworldcup.service.game.impl;

import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.fixtures.UserAccountFixtures;
import com.example.ourworldcup.fixtures.WorldcupFixtures;
import com.example.ourworldcup.repository.RoundRepository;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("비즈니스 로직 - Game")
@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    @InjectMocks
    private GameServiceImpl sut;
    @Mock
    WorldcupService worldcupService;
    @Mock
    RoundRepository roundRepository;

    @DisplayName("RoundType과 worldcup이 정해지면, 게임이 생성된다.")
    @Test
    void createGame() {
        //given
        Worldcup worldcup = WorldcupFixtures.createDefaultWorldcupWithItemsOf(8L);
        UserAccount userAccount = UserAccountFixtures.createDefaultUserAccount();
        //when
        Game game = sut.createGame(userAccount, worldcup, RoundType.ROUND8, PickType.ORDER);
        //then
        assertThat(game).isNotNull();

    }
}