package com.example.ourworldcup.domain.auth;

import com.example.ourworldcup.domain.enums.AuthorityType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@ToString
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    AuthorityType authorityType;


    @Override
    public String getAuthority() {
        return this.authorityType.getAuthority();
    }
}
