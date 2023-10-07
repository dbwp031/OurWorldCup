package com.example.ourworldcup.service.userAccount;

import com.example.ourworldcup.domain.auth.Authority;
import com.example.ourworldcup.domain.auth.Role;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.enums.AuthorityType;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.domain.relation.UserAccountRole;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.repository.AuthorityRepository;
import com.example.ourworldcup.repository.RoleRepository;
import com.example.ourworldcup.repository.UserAccountRepository;
import com.example.ourworldcup.repository.UserAccountRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountServiceImpl implements UserAccountService{
    private final RoleRepository roleRepository;
    private final UserAccountRoleRepository userAccountRoleRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    @Override
    public void setRoles(UserAccount userAccount, List<RoleType> roles) {
        for (RoleType roleType : roles) {
            Role role = roleRepository.findByRoleType(roleType);
            UserAccountRole userAccountRole =  UserAccountRole.builder()
                    .role(role)
                    .userAccount(userAccount)
                    .build();
            userAccountRoleRepository.save(userAccountRole);
        }
    }
    @Transactional
    @Override
    public List<Authority> getAuthorities(UserAccount userAccount) {
        Set<Authority> authorities = new HashSet<>();
        for (UserAccountRole userAccountRole : userAccount.getUserAccountRoles()) {
            Role role = userAccountRole.getRole();
            for (AuthorityType authorityType : role.getRoleType().getAuthorityTypes()) {
                Authority authority = authorityRepository.findByAuthorityType(authorityType)
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.AUTHORITY_NOT_FOUND.getMessage()));
            }
        }
        List<Authority> authorityList = new ArrayList<Authority>(authorities);
        return authorityList;
    }

    @Override
    public List<RoleType> getRoleTypes(UserAccount userAccount) {
        return getRole(userAccount).stream()
                .map(Role::getRoleType).toList();
    }
    @Override
    public List<Role> getRole(UserAccount userAccount) {
        List<Role> roles = userAccount.getUserAccountRoles().stream()
                .map(UserAccountRole::getRole).toList();
        return roles;
    }
    @Transactional
    @Override
    public Optional<UserAccount> findByEmailAndAuthProviderType(String email, AuthProviderType authProviderType) {
        return userAccountRepository.findByEmailAndAuthProvider_AuthProviderType(email, authProviderType);

    }
}
