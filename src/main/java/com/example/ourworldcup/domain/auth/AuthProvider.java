package com.example.ourworldcup.domain.auth;

import com.example.ourworldcup.domain.enums.AuthProviderType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class AuthProvider {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Enumerated(EnumType.STRING)
    private AuthProviderType authProviderType;
}
