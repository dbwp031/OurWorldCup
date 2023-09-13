package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.Worldcup;

import java.util.List;

public interface WorldcupService {
    Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto);

    WorldcupResponseDto.WorldcupItemsDto toWorldcupItemsDto(Worldcup worldcup);
}
