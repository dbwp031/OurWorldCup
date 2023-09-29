package com.example.ourworldcup.service.invitation;

import com.example.ourworldcup.domain.Invitation;
import com.example.ourworldcup.domain.Worldcup;

import java.util.Optional;

public interface InvitationService {
    Invitation createInvitation(Worldcup worldcup);

    void deleteInvitation(Invitation invitation);

    Invitation getFreshInvitation(Worldcup worldcup);
    Boolean isExpired(Invitation invitation);
    String getInvitationUrl(Invitation invitation);

    Optional<Invitation> findByUuid(String uuid);



}
