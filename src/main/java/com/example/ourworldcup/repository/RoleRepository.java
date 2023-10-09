package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.Role;
import com.example.ourworldcup.domain.enums.RoleType;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
