package com.example.ourworldcup.service.item;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;

import java.io.IOException;

public interface ItemImageService {
    String loadBase64Image(Item item);
    String loadBase64Image(ItemImage itemImage) throws IOException;
}
