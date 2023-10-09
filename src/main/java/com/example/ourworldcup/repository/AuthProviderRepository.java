package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.AuthProvider;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface AuthProviderRepository extends JpaRepository<AuthProvider, Long> {
    Optional<AuthProvider> findByAuthProviderType(AuthProviderType authProviderType);
}
