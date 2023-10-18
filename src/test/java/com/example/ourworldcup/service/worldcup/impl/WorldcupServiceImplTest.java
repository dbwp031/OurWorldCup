package com.example.ourworldcup.service.worldcup.impl;

import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.fixtures.WorldcupFixtures;
import com.example.ourworldcup.repository.WorldcupRepository;
import com.example.ourworldcup.service.worldcup.WorldcupServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("비즈니스 로직 - Worldcup")
@ExtendWith(MockitoExtension.class)
class WorldcupServiceImplTest {
    @InjectMocks
    private WorldcupServiceImpl sut; // system under test
    @Mock
    private WorldcupRepository worldcupRepository;
    @Mock
    private FileService fileService;

    @DisplayName("월드컵이 주어지면, 가능한 round type이 모두 주어지는 지 확인한다.")
    @Test
    void validAllRoundTypes_basedOnWorldcup() {
        // Given
        Worldcup worldcup = WorldcupFixtures.createDefaultWorldcupWithItemsOf(7L);
        List<RoundType> expected = Arrays.asList(RoundType.ROUND4);
        // When
        List<RoundType> roundTypes = sut.getSupportedRoundTypes(worldcup);
        // Then
        assertThat(roundTypes).isEqualTo(expected);

    }

    @DisplayName("월드컵 아이템 개수에 따라, round type이 가능한지에 대한 참/거짓 값을 반환한다.")
    @MethodSource
    @ParameterizedTest(name = "[{index}] item count: {0}, round type: {1} => {2} ")
    void validRoundType_basedOnNumberOfWorldcupItems(Long itemCount, RoundType roundType, Boolean expected) {
        // given
        Worldcup worldcup = WorldcupFixtures.createDefaultWorldcupWithItemsOf(itemCount);

        // when
        Boolean canSupport = sut.canSupportRoundType(worldcup, roundType);

        // then
        assertThat(canSupport).isEqualTo(expected);
    }

    static Stream<Arguments> validRoundType_basedOnNumberOfWorldcupItems() {
        return Stream.of(
                Arguments.arguments(0L, RoundType.ROUND2, false),
                Arguments.arguments(1L, RoundType.ROUND2, false),
                Arguments.arguments(2L, RoundType.ROUND2, true),
                Arguments.arguments(2L, RoundType.ROUND4, false),
                Arguments.arguments(3L, RoundType.ROUND4, false),
                Arguments.arguments(7L, RoundType.ROUND4, true),
                Arguments.arguments(7L, RoundType.ROUND8, false),
                Arguments.arguments(8L, RoundType.ROUND8, true)
        );
    }
}