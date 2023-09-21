package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.auth.Role;
import com.example.ourworldcup.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
