package com.example.ourworldcup.service.userAccount;

import com.example.ourworldcup.domain.auth.Authority;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.domain.userAccount.UserAccount;

import java.util.List;

public interface UserAccountService {
    void setRoles(UserAccount userAccount, List<RoleType> roles);

    List<Authority> getAuthorities(UserAccount userAccount);

    }
