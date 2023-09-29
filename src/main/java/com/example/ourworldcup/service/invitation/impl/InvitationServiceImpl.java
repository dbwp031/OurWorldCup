package com.example.ourworldcup.service.invitation.impl;

import com.example.ourworldcup.config.properties.SecurityProperties;
import com.example.ourworldcup.domain.Invitation;
import com.example.ourworldcup.domain.Uuid;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.repository.InvitationRepository;
import com.example.ourworldcup.repository.uuid.UuidRepository;
import com.example.ourworldcup.service.invitation.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class InvitationServiceImpl implements InvitationService {
    private static final long INVITATION_VALIDITY_IN_SEC = 60 * 60 * 24;

    private final UuidRepository uuidRepository;
    private final InvitationRepository invitationRepository;
    private final SecurityProperties securityProperties;

    @Override
    public Invitation createInvitation(Worldcup worldcup) {
        Uuid invitationUuid = createUUID();
        return Invitation.builder()
                .worldcup(worldcup)
                .uuid(invitationUuid)
                .build();
    }

    @Override
    public void deleteInvitation(Invitation invitation) {
        invitationRepository.delete(invitation);
    }

    @Override
    public Invitation getFreshInvitation(Worldcup worldcup) {
        Invitation candidate = worldcup.getInvitation();
        if (this.isExpired(candidate)) {
            this.deleteInvitation(candidate);
            Invitation invitation = this.createInvitation(worldcup);
            return invitation;
        }
        return candidate;
    }

    @Override
    public Boolean isExpired(Invitation invitation) {
        return LocalDateTime.now().isAfter(invitation.getModifiedAt().plusSeconds(INVITATION_VALIDITY_IN_SEC));
    }

    @Override
    public String getInvitationUrl(Invitation invitation) {
        String invitationUrl = UriComponentsBuilder.newInstance()
                .scheme(securityProperties.getScheme())
                .port(securityProperties.getPort())
                .host(securityProperties.getDefaultHost())
                .path("/worldcup/join")
                .queryParam("token", invitation.getUuid().getUuid())
                .toUriString();

        return invitationUrl;
    }

    @Override
    public Optional<Invitation> findByUuid(String uuid) {
        return invitationRepository.findByUuid_Uuid(uuid);
    }

    public Uuid createUUID() {
        Uuid savedUuid = null;
        String candidate = UUID.randomUUID().toString();
        Boolean doesExist = uuidRepository.exist(candidate);
        if (Boolean.TRUE.equals(doesExist)) {
            savedUuid = createUUID();
        } else {
            savedUuid = uuidRepository.save(Uuid.builder()
                    .uuid(candidate).build());
        }
        return savedUuid;
    }
}
