package com.example.ourworldcup.fixtures;

import com.example.ourworldcup.domain.ItemImage;
import com.example.ourworldcup.domain.Uuid;

public class ItemImageFixtures {
    private static final String TEST_URL = "TEST URL: ";
    private static final String TEST_FILE_NAME = "TEST FILE NAME: ";

    public static ItemImage createDefaultItemImage() {
        Uuid uuid = UuidFixtures.createDefaultUuid();
        return createItemImage(1L, uuid);
    }

    public static ItemImage createItemImage(Long id, Uuid uuid) {

        return ItemImage.builder()
                .id(id)
                .uuid(uuid)
                .url(TEST_URL + id)
                .fileName(TEST_FILE_NAME + id)
                .build();
    }
}
