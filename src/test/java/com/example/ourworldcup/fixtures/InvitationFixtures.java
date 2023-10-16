package com.example.ourworldcup.fixtures;

import com.example.ourworldcup.domain.Invitation;
import com.example.ourworldcup.domain.Uuid;

public class InvitationFixtures {
    public static Invitation createInvitation(Long id, Uuid uuid) {
        return Invitation.builder()
                .id(id)
                .uuid(uuid)
                .build();
    }
}
