package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.AuthProvider;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmailAndAuthProvider_AuthProviderType(String email, AuthProviderType authProviderType);
}
