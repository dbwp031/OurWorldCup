package com.example.ourworldcup.auth.handler;

import com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.ourworldcup.config.properties.SecurityProperties;
import com.example.ourworldcup.util.component.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@AllArgsConstructor
@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response);
        exception.printStackTrace();
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String defaultUrl = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(securityProperties.getDefaultHost())
                .path(securityProperties.getDefaultFailurePath()).toUriString();
        Optional<String> redirectUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .map((path) -> UriComponentsBuilder.newInstance()
                        .scheme("https")
                        .host(securityProperties.getDefaultHost())
                        .path(path).toUriString());
        String targetUrl = redirectUrl.orElse(defaultUrl);
        return targetUrl;
    }
}
