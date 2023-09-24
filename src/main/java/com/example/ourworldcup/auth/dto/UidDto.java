package com.example.ourworldcup.auth.dto;

import com.example.ourworldcup.domain.enums.AuthProviderType;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UidDto {
    private String email;
    private AuthProviderType authProviderType;
}
