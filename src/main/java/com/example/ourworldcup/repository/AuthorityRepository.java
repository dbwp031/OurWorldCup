package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
