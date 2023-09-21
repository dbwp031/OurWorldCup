package com.example.ourworldcup.auth.service;

import com.example.ourworldcup.auth.dto.OAuthAttributes;
import com.example.ourworldcup.domain.auth.AuthProvider;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.repository.AuthProviderRepository;
import com.example.ourworldcup.repository.UserAccountRepository;
import com.example.ourworldcup.service.userAccount.UserAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AuthProviderRepository authProviderRepository;
    private final UserAccountService userAccountService;
    private final UserAccountRepository userAccountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        return getOAuth2UserFromResource(userRequest, oAuth2User);
    }

    private OAuth2User getOAuth2UserFromResource(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Map<String, Object> modifiableAttributes = new HashMap<>(attributes.getAttributes());
        modifiableAttributes.put("registrationId", registrationId);
        modifiableAttributes.put("userNameAttributeName", userNameAttributeName);
        UserAccount userAccount = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                userAccountService.getAuthorities(userAccount),
                modifiableAttributes,
                attributes.getNameAttributeKey()
        );
    }

    private UserAccount saveOrUpdate(OAuthAttributes attributes) {
        AuthProvider authProvider =  authProviderRepository.findByAuthProviderType(attributes.getAuthProviderType())
                .orElseThrow(EntityNotFoundException::new);
        UserAccount userAccount = userAccountRepository.findByEmailAndAuthProvider(attributes.getEmail(), authProvider)
                .map(entity -> entity.update(attributes.getName()))
                .orElseGet(() -> newUserAccount(attributes));
        return userAccount;
    }

    private UserAccount newUserAccount(OAuthAttributes oAuthAttributes) {
        UserAccount userAccount = oAuthAttributes.toEntity(this.authProviderRepository);
        userAccountService.setRoles(userAccount, Arrays.asList(RoleType.USER));
        return userAccount;
    }
}
