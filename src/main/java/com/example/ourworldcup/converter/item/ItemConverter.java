package com.example.ourworldcup.converter.item;

import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter {
    public static WorldcupResponseDto.WorldcupItemDto toWorldcupItemDto(Item item, FileService fileService) throws Exception {
        return WorldcupResponseDto.WorldcupItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .base64Image(fileService.loadBase64Image(item.getItemImage()))
                .build();
    }
}
