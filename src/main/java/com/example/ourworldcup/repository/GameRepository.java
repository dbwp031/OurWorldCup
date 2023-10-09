package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.game.Game;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Hidden
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByWorldcupId(Long worldcupId);

    List<Game> findAllByPlayer_id(Long userAccountId);
}
