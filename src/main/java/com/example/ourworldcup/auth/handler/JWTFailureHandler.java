package com.example.ourworldcup.auth.handler;

import com.example.ourworldcup.exception.handler.JwtAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

// JwtAuthFilter에서 AuthenticatioManager가 인증 실패했을 때 사용
public class JWTFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        JwtAuthenticationException jwtAuthenticationException = (JwtAuthenticationException) exception;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Cookie accessToken = getCookieValue(request, "accessToken").orElse(new Cookie("accessToken", null));
        Cookie refreshToken = getCookieValue(request, "refreshToken").orElse(new Cookie("refreshToken", null));
        accessToken.setMaxAge(0);
        refreshToken.setMaxAge(0);
        response.addCookie(accessToken);
        response.addCookie(refreshToken);
    }

    private Optional<Cookie> getCookieValue(HttpServletRequest request, String cookieName) {
        if(request.getCookies()==null) return Optional.empty();
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst();
    }
}
