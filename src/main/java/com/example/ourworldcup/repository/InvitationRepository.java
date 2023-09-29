package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByUuid_Uuid(String uuid);
}
