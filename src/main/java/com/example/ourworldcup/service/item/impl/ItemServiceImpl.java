package com.example.ourworldcup.service.item.impl;

import com.example.ourworldcup.aws.s3.metadata.ItemImagePackageMetadata;
import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.Uuid;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.repository.WorldcupRepository;
import com.example.ourworldcup.repository.item.ItemRepository;
import com.example.ourworldcup.repository.uuid.UuidRepository;
import com.example.ourworldcup.service.fileProcess.ItemImageProcess;
import com.example.ourworldcup.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UuidRepository uuidRepository;
    private final WorldcupRepository worldcupRepository;
    private final ItemImageProcess  itemImageProcess;

    @Override
    @Transactional
    public Item saveItem(ItemRequestDto.ItemCreateRequestDto itemCreateRequestDto, Worldcup worldcup) {
        Worldcup realWorldcup = worldcupRepository.getReferenceById(worldcup.getId());
        String itemTitle = itemCreateRequestDto.getTitle();
        Boolean isExist = itemRepository.checkExistByItemTitleInSameWorldcup(realWorldcup.getId(), itemTitle);
        if (isExist) {
            throw new IllegalStateException(String.format("이미 같은 이름의 아이템이 월드컵에 추가되어 있습니다. (TITLE: %s)", itemTitle));
        } else {
            Item item = Item.builder()
                    .worldcup(realWorldcup)
                    .title(itemTitle)
                    .build();
            System.out.println("아이템 추가 전: "+ realWorldcup.getItems().toString());
            realWorldcup.addItem(item);
            System.out.println("아이템 추가 후: "+ realWorldcup.getItems().toString());

            String uuid = UUID.randomUUID().toString();
            Uuid uuidEntity = Uuid.builder()
                    .uuid(uuid).build();

            ItemImagePackageMetadata itemImagePackageMetadata = ItemImagePackageMetadata.builder()
                    .fileName(itemCreateRequestDto.getImage().getOriginalFilename())
                    .uuid(uuid)
                    .uuidEntity(uuidEntity).build();

            item = itemImageProcess.uploadImageAndMapToItem(itemCreateRequestDto.getImage(), itemImagePackageMetadata, item);
            uuidRepository.save(uuidEntity);
            itemRepository.save(item);
//            worldcupRepository.save(worldcup);
            return item;
        }
    }

    @Transactional
    @Override
    public void deleteItem(Long id) {
        Item item = this.findById(id);
        item.getWorldcup().getItems().remove(item);
        itemRepository.deleteById(id);
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 item id입니다."));
    }


    @Transactional
    public Long save(Item item) {
        return itemRepository.save(item).getId();
    }
}
