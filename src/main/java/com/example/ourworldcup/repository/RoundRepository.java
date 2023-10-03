package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.match.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findAllByGameId(Long id);
}
