package com.example.ourworldcup.auth.dto;

import com.example.ourworldcup.domain.auth.AuthProvider;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAccountDto {
    private Long id;
    private String email;
    private String name;
    private String picture;
    private AuthProviderType authProviderType;

    @Builder
    UserAccountDto(Long id, String email, String name, String picture, AuthProviderType authProviderType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.authProviderType = authProviderType;
    }

}
