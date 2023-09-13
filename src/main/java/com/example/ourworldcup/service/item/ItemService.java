package com.example.ourworldcup.service.item;

import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.Worldcup;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    Item saveItem(ItemRequestDto.ItemCreateRequestDto itemCreateRequestDto, Worldcup worldcup);

}
