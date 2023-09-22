package com.example.ourworldcup.converter.userAccount;

import com.example.ourworldcup.auth.dto.OAuthAttributes;
import com.example.ourworldcup.auth.dto.UserAccountDto;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.repository.UserAccountRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserAccountConverter {
    private final UserAccountRepository userAccountRepository;
    private static UserAccountRepository staticUserAccountRepository;

    @PostConstruct
    public void init() {
        this.staticUserAccountRepository = userAccountRepository;
    }

    public static UserAccountDto toUserAccountDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        String registrationId = oAuth2User.getAttribute("registrationId");
        String userNameAttributeName = oAuth2User.getAttribute("userNameAttributeName");
        Map<String, Object> cloned = new HashMap<>(attributes);
        cloned.put("response", attributes);
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, cloned);
        return UserAccountDto.builder()
                .email(oAuthAttributes.getEmail())
                .authProviderType(oAuthAttributes.getAuthProviderType())
                .name(oAuthAttributes.getName())
                .picture(oAuthAttributes.getPicture())
                .build();
    }

}
