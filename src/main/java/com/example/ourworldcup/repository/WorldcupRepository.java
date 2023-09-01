package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.Worldcup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldcupRepository extends JpaRepository<Worldcup, Long> {
}
