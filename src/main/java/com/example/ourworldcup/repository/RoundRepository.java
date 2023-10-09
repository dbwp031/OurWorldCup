package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.match.Round;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Hidden
public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findAllByGameId(Long id);
}
