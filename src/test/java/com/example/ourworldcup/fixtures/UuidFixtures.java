package com.example.ourworldcup.fixtures;

import com.example.ourworldcup.domain.Uuid;

public class UuidFixtures {
    private static final String TEST_UUID = "TEST UUID";

    public static Uuid createDefaultUuid() {
        return createUuid(1L);
    }

    public static Uuid createUuid(Long id) {
        return Uuid.builder()
                .id(id)
                .uuid(TEST_UUID + id)
                .build();
    }
}
