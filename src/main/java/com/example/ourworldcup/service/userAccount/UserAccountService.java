package com.example.ourworldcup.service.userAccount;

import com.example.ourworldcup.domain.auth.Authority;
import com.example.ourworldcup.domain.auth.Role;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.domain.userAccount.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    UserAccount findById(Long id);

    void setRoles(UserAccount userAccount, List<RoleType> roles);

    List<Authority> getAuthorities(UserAccount userAccount);

    Optional<UserAccount> findByEmailAndAuthProviderType(String email, AuthProviderType authProviderType);

    List<Role> getRole(UserAccount userAccount);

    List<RoleType> getRoleTypes(UserAccount userAccount);

}
