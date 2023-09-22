package com.example.ourworldcup.config;

import com.example.ourworldcup.auth.filter.JwtAuthFilter;
import com.example.ourworldcup.auth.filter.JwtExceptionInterceptorFilter;
import com.example.ourworldcup.auth.handler.CustomAuthenticationEntryPoint;
import com.example.ourworldcup.auth.handler.JWTFailureHandler;
import com.example.ourworldcup.auth.handler.OAuth2FailureHandler;
import com.example.ourworldcup.auth.handler.OAuth2SuccessHandler;
import com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.ourworldcup.auth.service.TokenService;
import com.example.ourworldcup.config.properties.SecurityProperties;
import com.example.ourworldcup.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;

@RequiredArgsConstructor
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Configuration
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final TokenService tokenService;
    private final UserAccountRepository userAccountRepository;
    private final SecurityProperties securityProperties;

    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    InMemoryClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
        OAuth2ClientPropertiesMapper mapper = new OAuth2ClientPropertiesMapper(oAuth2ClientProperties);
        List<ClientRegistration> registrations = mapper.asClientRegistrations().values().stream().toList();
        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    JwtAuthFilter jwtAuthFilter() throws Exception {
        return new JwtAuthFilter(authenticationManager(authenticationConfiguration), new JWTFailureHandler() );
    }

    @Bean
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
    @Bean
    JwtExceptionInterceptorFilter jwtExceptionInterceptorFilter() {
        return new JwtExceptionInterceptorFilter(customAuthenticationEntryPoint());
    }
    @Bean
    OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenService, userAccountRepository, oAuth2AuthorizationRequestBasedOnCookieRepository(), securityProperties);
    }

    @Bean
    OAuth2FailureHandler oAuth2FailureHandler() {
        return new OAuth2FailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository(), securityProperties);
    }
}
