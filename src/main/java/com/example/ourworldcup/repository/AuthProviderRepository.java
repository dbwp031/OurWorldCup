package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, Long> {
}
