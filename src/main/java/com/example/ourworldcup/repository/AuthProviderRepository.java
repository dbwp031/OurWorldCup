package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.AuthProvider;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, Long> {
    Optional<AuthProvider> findByAuthProviderType(AuthProviderType authProviderType);
}
