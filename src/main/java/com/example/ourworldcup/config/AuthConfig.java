package com.example.ourworldcup.config;

import com.example.ourworldcup.auth.filter.JwtAuthFilter;
import com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.ourworldcup.auth.service.CustomOAuth2UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class AuthConfig {
    private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;
    private final SecurityConfig securityConfig;
    private static CustomOAuth2UserService staticCustomOAuth2UserService;
    private static OAuth2AuthorizationRequestBasedOnCookieRepository staticOAuth2AuthorizationRequestBasedOnCookieRepository;
    private static SecurityConfig staticSecurityConfig;

    @PostConstruct
    public void init() {
        staticCustomOAuth2UserService = this.customOAuth2UserService;
        staticOAuth2AuthorizationRequestBasedOnCookieRepository = this.oAuth2AuthorizationRequestBasedOnCookieRepository;
        staticSecurityConfig = this.securityConfig;
    }

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
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(staticOAuth2AuthorizationRequestBasedOnCookieRepository)))
                .clientRegistrationRepository(inMemoryClientRegistrationRepository)
                .userInfoEndpoint(userInfo -> userInfo.userService(staticCustomOAuth2UserService))
                .defaultSuccessUrl("/login-success"));

        http.addFilterAt(staticSecurityConfig.jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }


}
