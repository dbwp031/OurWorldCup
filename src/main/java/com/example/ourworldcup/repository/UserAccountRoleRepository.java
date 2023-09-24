package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.relation.UserAccountRole;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRoleRepository extends JpaRepository<UserAccountRole, Long> {
}
