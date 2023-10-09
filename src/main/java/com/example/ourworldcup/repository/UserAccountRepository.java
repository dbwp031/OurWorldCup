package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmailAndAuthProvider_AuthProviderType(String email, AuthProviderType authProviderType);
}
