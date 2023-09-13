package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.aws.s3.AmazonS3Service;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.repository.WorldcupRepository;
import com.example.ourworldcup.service.item.ItemImageService;
import com.example.ourworldcup.service.item.impl.ItemImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.World;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class WorldcupServiceImpl implements WorldcupService{
    private final WorldcupRepository worldcupRepository;
    private final ItemImageService itemImageService;
    private final AmazonS3Service amazonS3Service;
    @Override
    public Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto) {
        // TODO: invitationCode 생성 및 저장 수정해야함.
        Worldcup worldcup = Worldcup.builder()
                .title(worldcupCreateRequestDto.getTitle())
                .content(worldcupCreateRequestDto.getContent())
                .password(worldcupCreateRequestDto.getPassword())
                .invitationCode("temp")
                .build();
        worldcupRepository.save(worldcup);
        return worldcup;
    }

    @Override
    public WorldcupResponseDto.WorldcupItemsDto toWorldcupItemsDto(Worldcup worldcup) {
        WorldcupResponseDto.WorldcupItemsDto worldcupItemsDto = WorldcupResponseDto.WorldcupItemsDto.builder().build();
        for (Item item : worldcup.getItems()) {
            worldcupItemsDto.getItems().put(item.getTitle(), itemImageService.loadBase64Image(item));
        }
        return worldcupItemsDto;
    }



}
