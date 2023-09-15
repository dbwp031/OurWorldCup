package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.aws.s3.AmazonS3Service;
import com.example.ourworldcup.aws.s3.FileService;
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
    private final FileService fileService;
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

    @Transactional
    @Override
    public void deleteItem(Long worldcupId, Long itemId) {
        Worldcup worldcup = worldcupRepository.findById(worldcupId)
                .orElseThrow(() -> new IllegalArgumentException("worldcup이 없습니다.: worldcup ID: " + worldcupId));
        System.out.println(worldcup.toString());
        Item item = worldcup.getItems().stream()
                .filter(itemDeleted -> itemDeleted.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("aaitem이 없습니다.: item ID: " + itemId));
        fileService.deleteFile(item.getItemImage().getFileName());
        worldcup.getItems().remove(item);
    }


}
