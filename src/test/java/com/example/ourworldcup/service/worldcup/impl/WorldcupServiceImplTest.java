package com.example.ourworldcup.service.worldcup.impl;

import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.domain.userAccount.UserAccount;
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

    @DisplayName("월드컵 생성 정보를 제공하여 생성하면, 월드컵이 올바르게 생성된다.")
    @Test
    void givenWorldcupCreateRequestDto_whenCreateWorldcup_thenReturnCorrectlyCreatedWorldcup() {
        // Given
        Long id = 1L;
        String title = "테스트 타이틀";
        String content = "테스트 컨텐츠";
        String password = "테스트 패스워드";
        WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto = WorldcupRequestDto.WorldcupCreateRequestDto.builder()
                .title(title)
                .content(content)
                .password(password).build();

        UserAccount userAccount = UserAccount.builder()
                .userId("userId")
                .userName("name")
                .email("email")
                .nickName("nickname")
                .build();
        // When
        Worldcup worldcup = sut.createWorldcup(worldcupCreateRequestDto, userAccount);
        // Then
        assertThat(worldcup).hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("content", content)
                .hasFieldOrPropertyWithValue("password", password);
    }

    //    @DisplayName("월드컵과 라운드 타입을 제공하면, 해당 라운드 타입이 월드컵에서 지원가능한지 참/거짓 값을 반환한다.")
//    @Test
//    void givenWorldcupAndRoundType_whenCheckRoundType_thenReturnBooleanWhetherWorldcupSupportsRoundType() {
//        // Given
//
//        // When
//
//        // Then
//    }
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