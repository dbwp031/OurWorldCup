package com.example.ourworldcup.controller.item.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDto {
    private String title;
    private String description;
    private MultipartFile image;

    public static ItemDto of(ItemRequestDto.ImageItemCreateRequestDto createRequestDto) {
        return ItemDto.builder()
                .image(createRequestDto.getImage())
                .title(null)
                .description(null)
                .build();
    }
}
