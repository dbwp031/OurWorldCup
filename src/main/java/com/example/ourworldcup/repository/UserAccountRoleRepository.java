package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.relation.UserAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRoleRepository extends JpaRepository<UserAccountRole, Long> {
}
