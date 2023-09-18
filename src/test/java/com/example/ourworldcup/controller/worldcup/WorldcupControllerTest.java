package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import com.example.ourworldcup.service.worldcup.WorldcupServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("View 컨트롤러 - 월드컵 생성")
@Import({})
@WebMvcTest(WorldcupController.class)
class WorldcupControllerTest {
    private final MockMvc mvc;
    @Mock private final WorldcupServiceImpl worldcupService;
    public WorldcupControllerTest(@Autowired MockMvc mvc,
                                  @Autowired WorldcupServiceImpl worldcupService) {
        this.mvc = mvc;
        this.worldcupService = worldcupService;
    }

    @DisplayName("[view] [GET] 월드컵 생성: 아이템 리스트 페이지 - 정상 호출")
    @Test
    public void given_when_then() {
        // Given
        
        // When

        // Then
    }

}