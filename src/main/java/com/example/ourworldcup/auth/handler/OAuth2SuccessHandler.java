package com.example.ourworldcup.auth.handler;

import com.example.ourworldcup.auth.dto.Token;
import com.example.ourworldcup.auth.dto.UserAccountDto;
import com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.ourworldcup.auth.service.TokenService;
import com.example.ourworldcup.config.properties.SecurityProperties;
import com.example.ourworldcup.converter.userAccount.UserAccountConverter;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.repository.UserAccountRepository;
import com.example.ourworldcup.util.component.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.IS_LOCAL;
import static com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@AllArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private TokenService tokenService;
    private UserAccountRepository userAccountRepository;
    private OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserAccountDto userAccountDto = UserAccountConverter.toUserAccountDto(oAuth2User);
        Token token = tokenService.generateToken(userAccountDto.getEmail(), userAccountDto.getAuthProviderType(), Arrays.asList(RoleType.USER));

        userAccountRepository.findByEmailAndAuthProvider_AuthProviderType(userAccountDto.getEmail(), userAccountDto.getAuthProviderType())
                .ifPresent((userAccount -> {
                    userAccount.setRefreshToken(token.getRefreshToken());
                    userAccountRepository.save(userAccount);
                }));

        Boolean isLocal = CookieUtil.getCookie(request, IS_LOCAL)
                .map(Cookie::getValue)
                .map(Boolean::parseBoolean).orElse(false);
        writeTokenResponse(request, response, token);
        String targetUrl = this.determineTargetUrlDelegate(request, response, token, isLocal);
        this.clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private String determineTargetUrlDelegate(HttpServletRequest request, HttpServletResponse response, Token token, Boolean isLocal) {
        if (isLocal) {
            String defaultUrl = UriComponentsBuilder.fromUriString("http://localhost:8080")
                    .path("login-success-is-local").toString();
            String redirectPath = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                    .map(Cookie::getValue)
                    .orElse(securityProperties.getDefaultSuccessPath());
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("accessToken", token.getAccessToken());
            params.add("refreshToken", token.getRefreshToken());
            params.add("redirectPath", redirectPath);
            String targetUrl = UriComponentsBuilder.newInstance()
                    .path(defaultUrl)
                    .queryParams(params).build().toString();
            return targetUrl;
        } else {
            return this.determineTargetUrl(request, response);
        }
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String defaultUrl = UriComponentsBuilder.newInstance()
                .scheme(securityProperties.getScheme())
                .port(securityProperties.getPort())
                .host(securityProperties.getDefaultHost())
                .path(securityProperties.getDefaultSuccessPath()).toUriString();
        Optional<String> redirectUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .map((path) -> UriComponentsBuilder.newInstance()
                        .scheme(securityProperties.getScheme())
                        .port(securityProperties.getPort())
                        .host(securityProperties.getDefaultHost())
                        .path(path).toUriString());
        String targetUrl = redirectUrl.orElse(defaultUrl);
        return targetUrl;
    }
    private void writeTokenResponse(HttpServletRequest request, HttpServletResponse response, Token token) {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        Boolean isLocal = CookieUtil.getCookie(request, IS_LOCAL)
                .map(Cookie::getValue)
                .map(Boolean::parseBoolean).orElse(false);

        ResponseCookie accessTokenCookie = CookieUtil.createAccessTokenCookie(token.getAccessToken(), isLocal);
        ResponseCookie refreshTokenCookie = CookieUtil.createRefreshTokenCookie(token.getRefreshToken(), isLocal);
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());    }


}
