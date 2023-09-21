package com.example.ourworldcup.config;

import com.example.ourworldcup.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class AuthConfig {
    private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/oauth2/**", "/assets/**", "/css/**", "/forms/**", "/js/**","/icons/**",
                        "/").permitAll()
                .anyRequest().authenticated());

        http.oauth2Login(oauth -> oauth
                .authorizationEndpoint((auth -> auth
                        .baseUri("/oauth2/authorize")))
                .clientRegistrationRepository(inMemoryClientRegistrationRepository)
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)));

        return http.build();
    }
}
