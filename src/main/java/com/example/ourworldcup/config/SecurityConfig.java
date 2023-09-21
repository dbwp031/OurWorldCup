package com.example.ourworldcup.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;

@RequiredArgsConstructor
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Configuration
public class SecurityConfig {
    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    InMemoryClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
        OAuth2ClientPropertiesMapper mapper = new OAuth2ClientPropertiesMapper(oAuth2ClientProperties);
        List<ClientRegistration> registrations = mapper.asClientRegistrations().values().stream().toList();
        return new InMemoryClientRegistrationRepository(registrations);
    }
}
