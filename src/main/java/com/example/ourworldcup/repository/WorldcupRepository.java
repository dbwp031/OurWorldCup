package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.Worldcup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorldcupRepository extends JpaRepository<Worldcup, Long> {
    Optional<Worldcup> findByInvitation_Uuid_Uuid(String uuid);
}
