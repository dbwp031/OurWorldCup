package com.example.ourworldcup.fixtures;

import com.example.ourworldcup.domain.*;

import java.util.stream.LongStream;

public class WorldcupFixtures {
    public static final String TEST_WORLDCUP_TITLE = "TEST WORLDCUP TITLE";
    public static final String TEST_WORLDCUP_CONTENT = "TEST WORLDCUP CONTENT";
    public static final String TEST_WORLDCUP_PASSWORD = "TEST WORLDCUP PASSWORD";

    public static Worldcup createDefaultWorldcup() {
        Uuid uuid = UuidFixtures.createUuid(1L);
        Invitation invitation = InvitationFixtures.createInvitation(1L, uuid);
        return createWorldcup(1L, invitation);
    }

    public static Worldcup createDefaultWorldcupWithItemsOf(Long itemCount) {
        Worldcup worldcup = createDefaultWorldcup();
        LongStream.range(1, 1 + itemCount).forEach(
                idx -> {
                    Uuid uuid = UuidFixtures.createUuid(idx);
                    ItemImage itemImage = ItemImageFixtures.createItemImage(idx, uuid);
                    Item item = ItemFixtures.createItem(idx, itemImage);

                    worldcup.addItem(item);
                }
        );
        return worldcup;
    }

    public static Worldcup createWorldcup(Long id, Invitation invitation) {
        Worldcup worldcup = Worldcup.builder()
                .id(id)
                .title(TEST_WORLDCUP_TITLE)
                .content(TEST_WORLDCUP_CONTENT)
                .password(TEST_WORLDCUP_PASSWORD)
                .build();
        worldcup.setInvitation(invitation);
        return worldcup;
    }
}
