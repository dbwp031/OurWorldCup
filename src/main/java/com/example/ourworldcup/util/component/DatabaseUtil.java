package com.example.ourworldcup.util.component;

import com.example.ourworldcup.domain.auth.AuthProvider;
import com.example.ourworldcup.domain.auth.Authority;
import com.example.ourworldcup.domain.auth.Role;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.enums.AuthorityType;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.repository.AuthProviderRepository;
import com.example.ourworldcup.repository.AuthorityRepository;
import com.example.ourworldcup.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseUtil {
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final AuthProviderRepository authProviderRepository;

    @Transactional
    @PostConstruct
    public void init() {
        // Authority와 Role 엔티티들을 만들어줘야 한다.
        long authorityNum = authorityRepository.count();
        if (authorityNum == 0) {
            for (AuthorityType authorityType : AuthorityType.values()) {
                Authority authority = Authority.builder()
                        .authorityType(authorityType)
                        .build();
                authorityRepository.save(authority);
            }
        } else if (authorityNum != AuthorityType.values().length) {
            throw new RuntimeException("Authority DB row 수가 비정상입니다.");
        }

        long roleNum = roleRepository.count();
        if (roleNum == 0) {
            for (RoleType roleType : RoleType.values()) {
                Role role = Role.builder()
                        .roleType(roleType)
                        .build();
                roleRepository.save(role);
            }
        } else if (roleNum != RoleType.values().length) {
            throw new RuntimeException("Role DB row 수가 비정상입니다.");
        }

        long authProviderNum = authProviderRepository.count();
        if (authProviderNum == 0) {
            for (AuthProviderType authProviderType : AuthProviderType.values()) {
                AuthProvider authProvider = AuthProvider.builder()
                        .authProviderType(authProviderType)
                        .build();
                authProviderRepository.save(authProvider);
            }
        } else if (authProviderNum != AuthProviderType.values().length) {
            throw new RuntimeException("AuthProviderType DB row 수가 비정상입니다.");
        }


        log.info("Type Entity 생성 완료. AuthorityType: {}개, RoleType: {}개, AuthProviderType: {}개",
                AuthorityType.values().length,
                RoleType.values().length,
                AuthProviderType.values().length
                );
    }
}
