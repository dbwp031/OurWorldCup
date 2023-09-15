package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Worldcup;

public interface WorldcupService {
    Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto);
}
