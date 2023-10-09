package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.relation.UserAccountRole;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface UserAccountRoleRepository extends JpaRepository<UserAccountRole, Long> {
}
