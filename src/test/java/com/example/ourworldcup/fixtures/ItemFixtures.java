package com.example.ourworldcup.fixtures;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.ItemImage;

public class ItemFixtures {
    private static final String TEST_ITEM_TITLE = "TEST ITEM TITLE";

    public static Item createDefaultItem() {
        ItemImage itemImage = ItemImageFixtures.createDefaultItemImage();
        return createItem(1L, itemImage);
    }

    public static Item createItem(Long id, ItemImage itemImage) {
        Item item = Item.builder()
                .id(id)
                .title(TEST_ITEM_TITLE + id)
                .itemImage(itemImage)
                .build();
        return item;
    }
}
