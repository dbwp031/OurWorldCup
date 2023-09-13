package com.example.ourworldcup.service.item.impl;

import com.example.ourworldcup.aws.s3.metadata.ItemImagePackageMetadata;
import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;
import com.example.ourworldcup.domain.Uuid;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.repository.item.ItemRepository;
import com.example.ourworldcup.repository.uuid.UuidRepository;
import com.example.ourworldcup.service.fileProcess.FileProcessService;
import com.example.ourworldcup.service.fileProcess.ItemImageProcess;
import com.example.ourworldcup.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UuidRepository uuidRepository;
    private final ItemImageProcess  itemImageProcess;
    @Override
    @Transactional
    public Item saveItem(ItemRequestDto.ItemCreateRequestDto itemCreateRequestDto, Worldcup worldcup) {
        String itemTitle = itemCreateRequestDto.getTitle();
        Boolean isExist = itemRepository.checkExistByItemTitleInSameWorldcup(worldcup.getId(), itemTitle);
        if (isExist) {
            throw new IllegalStateException(String.format("이미 같은 이름의 아이템이 월드컵에 추가되어 있습니다. (TITLE: %s)", itemTitle));
        } else {

            Item item = Item.builder()
                    .worldcup(worldcup)
                    .title(itemTitle)
                    .build();

            String uuid = UUID.randomUUID().toString();
            Uuid uuidEntity = Uuid.builder()
                    .uuid(uuid).build();

            ItemImagePackageMetadata itemImagePackageMetadata = ItemImagePackageMetadata.builder()
                    .fileName(itemCreateRequestDto.getImage().getOriginalFilename())
                    .uuid(uuid)
                    .uuidEntity(uuidEntity).build();

            item = itemImageProcess.uploadImageAndMapToItem(itemCreateRequestDto.getImage(), itemImagePackageMetadata, item);

            worldcup.getItems().add(item);
            uuidRepository.save(uuidEntity);
            itemRepository.save(item);
            return item;
        }
    }


    @Transactional
    public Long save(Item item) {
        return itemRepository.save(item).getId();
    }
}
