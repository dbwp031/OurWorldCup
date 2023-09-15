package com.example.ourworldcup.service.item.impl;

import com.example.ourworldcup.aws.s3.metadata.ItemImagePackageMetadata;
import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import com.example.ourworldcup.domain.Uuid;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.repository.item.ItemRepository;
import com.example.ourworldcup.repository.uuid.UuidRepository;
import com.example.ourworldcup.service.fileProcess.ItemImageProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@DisplayName("비즈니스 로직 - ItemSErvice")
@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @InjectMocks private ItemServiceImpl sut;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemImageProcess itemImageProcess;

    @Mock
    UuidRepository uuidRepository;

    @Test
    void given_when_then() throws IOException {
        //given

        Long worldcupId = 1L;
        Long itemId = 1L;
        String realUuid = "uuid string";
        String fileName = "itemImage filename";
        String itemImageUrl = "itemImage url";
        String worldcup_title = "worldcup title";
        Worldcup worldcup = Worldcup.builder()
                .id(worldcupId)
                .title(worldcup_title)
                .content("worldcup content")
                .password("worldcup password").build();

        InputStream contentStream = new ByteArrayInputStream("file content".getBytes());

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", // form parameter name
                fileName, // filename
                "text/plain", // content type
                contentStream // file content
        );
        String itemTitle = "ItemCreateRequestDto title";
        ItemRequestDto.ItemCreateRequestDto itemCreateRequestDto = ItemRequestDto.ItemCreateRequestDto.builder()
                .title(itemTitle)
                .image(mockMultipartFile)
                .build();

        Uuid uuid = Uuid.builder()
                .id(1L)
                .uuid(realUuid)
                .build();

        ItemImage itemImage = ItemImage.builder()
                .id(1L)
                .url(itemImageUrl)
                .fileName(fileName)
                .uuid(uuid).build();

        Item item = Item.builder()
                .id(itemId)
                .worldcup(worldcup)
                .title(itemTitle)
                .itemImage(itemImage).build();


        given(itemRepository.checkExistByItemTitleInSameWorldcup(worldcupId, itemTitle)).willReturn(false);
        given(itemImageProcess.uploadImageAndMapToItem(any(MultipartFile.class), any(ItemImagePackageMetadata.class), any(Item.class))).willReturn(item);

        //when
        sut.saveItem(itemCreateRequestDto, worldcup);

        //then
        assertThat(worldcup.getItems()).hasSize(1);
    }
}