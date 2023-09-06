package com.example.ourworldcup.service;

import com.example.ourworldcup.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

}
