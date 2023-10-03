package com.example.ourworldcup.converter.item;

import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.item.dto.ItemResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class ItemConverter {
    private final FileService fileService;
    private static FileService staticFileService;
    @PostConstruct
    public void init() {
        staticFileService = fileService;
    }
    public static WorldcupResponseDto.WorldcupItemDto toWorldcupItemDto(Item item) throws Exception {
        return WorldcupResponseDto.WorldcupItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .base64Image(staticFileService.loadBase64Image(item.getItemImage()))
                .build();
    }

    public static ItemResponseDto.BasicDto toItemResponseBasicDto(Item item) throws Exception {
        return ItemResponseDto.BasicDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .worldcupId(item.getWorldcup().getId())
                .base64ItemImage(staticFileService.loadBase64Image(item.getItemImage()))
                .build();
    }
}
