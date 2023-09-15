package com.example.ourworldcup.service.worldcup.impl;

import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import com.example.ourworldcup.domain.Uuid;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.repository.WorldcupRepository;
import com.example.ourworldcup.service.worldcup.WorldcupServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - Worldcup")
@ExtendWith(MockitoExtension.class)
class WorldcupServiceImplTest {
    @InjectMocks private WorldcupServiceImpl sut; // system under test
    @Mock private  WorldcupRepository worldcupRepository;
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
        // When
        Worldcup worldcup = sut.createWorldcup(worldcupCreateRequestDto);
        // Then
        assertThat(worldcup).hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("content", content)
                .hasFieldOrPropertyWithValue("password", password);
    }
    @DisplayName("월드컵 번호와 아이템 번호가 주어지면, 아이템이 삭제된다.")
    @Test
    void givenWorldcupIdAndItemId_whenDeletesItem_thenCorrectlyDeletesItem() {
        //given
        Long worldcupId = 1L;
        Long itemId = 1L;
        String realUuid = "uuid string";
        String fileName = "itemImage filename";
        Worldcup worldcup = Worldcup.builder()
                .id(worldcupId)
                .title("worldcup title")
                .content("worldcup content")
                .password("worldcup password").build();
        Uuid uuid = Uuid.builder()
                .id(1L)
                .uuid(realUuid)
                .build();
        ItemImage itemImage = ItemImage.builder()
                .id(1L)
                .url("itemImage url")
                .fileName(fileName)
                .uuid(uuid).build();

        Item item = Item.builder()
                .id(itemId)
                .worldcup(worldcup)
                .title("item title")
                .itemImage(itemImage).build();
        worldcup.getItems().add(item);
        given(worldcupRepository.findById(worldcupId)).willReturn(Optional.of(worldcup));
        //when
        sut.deleteItem(worldcupId, itemId);
        //then
        assertThat(worldcup.getItems()).doesNotContain(item);
    }
}