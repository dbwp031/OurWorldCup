package com.example.ourworldcup.auth.dto;

import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.repository.AuthProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Builder
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String nickname;
    private String picture;
    private AuthProviderType authProviderType;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao("id", attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authProviderType(AuthProviderType.KAKAO)
                .build();
    }

    public UserAccount toEntity(AuthProviderRepository authProviderRepository) {
        String userId = splitEmail(email);
        UserAccount newUserAccount = UserAccount.builder()
                .userId(userId)
                .userName(name)
                .nickName(userId)
                .email(email)
                .picture(picture)
                .authProvider(authProviderRepository.findByAuthProviderType(this.authProviderType)
                        .orElseThrow(EntityNotFoundException::new))
                .build();
        return newUserAccount;
    }

    public String splitEmail(String email) {
        if (email == null) {
            return "nullEmail";
        }
        return email.split("@")[0];
    }
}
