package com.example.ourworldcup.domain.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RoleType{
    ADMIN("ROLE_ADMIN", Arrays.asList(AuthorityType.READ, AuthorityType.WRITE, AuthorityType.UPDATE, AuthorityType.DELETE)),
    USER("ROLE_USER", Arrays.asList(AuthorityType.READ, AuthorityType.WRITE, AuthorityType.UPDATE, AuthorityType.DELETE)),
    GUEST("ROLE_GUEST", Arrays.asList(AuthorityType.READ, AuthorityType.WRITE, AuthorityType.UPDATE, AuthorityType.DELETE));

    private final String key;
    private final List<AuthorityType> authorityTypes;
    RoleType(String key, List<AuthorityType> authorityTypes) {
        this.key = key;
        this.authorityTypes = authorityTypes;
    }
    public List<AuthorityType> getAuthorityTypes() {
        return authorityTypes;
    }
}
