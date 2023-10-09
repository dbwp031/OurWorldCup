package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.Invitation;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByUuid_Uuid(String uuid);

    boolean existsByUuid_Uuid(String uuid);
}
