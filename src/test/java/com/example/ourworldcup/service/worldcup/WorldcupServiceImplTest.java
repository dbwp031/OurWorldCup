package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.repository.WorldcupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("비즈니스 로직 - Worldcup")
@ExtendWith(MockitoExtension.class)
class WorldcupServiceImplTest {
    @InjectMocks private  WorldcupServiceImpl sut; // system under test
    @Mock private  WorldcupRepository worldcupRepository;

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
        // When
        Worldcup worldcup = sut.createWorldcup(worldcupCreateRequestDto);
        // Then
        assertThat(worldcup).hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("content", content)
                .hasFieldOrPropertyWithValue("password", password);
    }

}