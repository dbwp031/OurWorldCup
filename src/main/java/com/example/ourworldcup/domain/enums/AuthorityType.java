package com.example.ourworldcup.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityType implements GrantedAuthority {
    READ("READ", "읽기"),
    WRITE("WRITE", "쓰기"),
    DELETE("DELETE", "지우기"),
    UPDATE("UPDATE", "업데이트");

    private String key;
    private String title;

    AuthorityType(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getAuthority() {
        return this.key.toUpperCase();
    }
}
