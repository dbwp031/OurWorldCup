package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
