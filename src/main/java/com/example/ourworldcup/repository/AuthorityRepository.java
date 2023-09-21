package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.Authority;
import com.example.ourworldcup.domain.enums.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityType(AuthorityType authorityType);
}
