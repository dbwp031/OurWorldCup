package com.example.ourworldcup.auth.filter;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import com.example.ourworldcup.auth.dto.Token;
import com.example.ourworldcup.auth.exception.JwtAuthenticationException;
import com.example.ourworldcup.auth.handler.JWTFailureHandler;
import com.example.ourworldcup.util.component.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.example.ourworldcup.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.IS_LOCAL;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTFailureHandler failureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getCookieValue(request, "accessToken");
        String refreshToken = getCookieValue(request, "refreshToken");
        if (accessToken == null && refreshToken == null) {
            // 둘 다 없다면, 바로 다음 필터로 넘어가라
            filterChain.doFilter(request, response);
        } else {
            try {
                Token jwtToken = new Token(accessToken, refreshToken);
                // 현재 JwtToken을 가져옴
                JwtAuthentication authenticationRequest = new JwtAuthentication(jwtToken, request, response);
                // authenticationResult를 기반으로 인증매니저가 인증시키고 그 결과를 authenticationResult에 담음
                JwtAuthentication authenticationResult = (JwtAuthentication) this.authenticationManager.authenticate(authenticationRequest);
                //authenticationResult를 SecurityContextHolder에 저장함 -> 아무 설정 안했으니 ContextHolder는 Session에 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authenticationResult);

                postAuthenticate(request, response, authenticationResult);
            } catch (JwtAuthenticationException ex) {
                // 인증이 실패하면 (AuthenticationException이 던져질테니) Context를 지워라
                SecurityContextHolder.clearContext();
                this.failureHandler.onAuthenticationFailure(request, response, ex);
                throw ex;
            } catch (Exception exception) {
                SecurityContextHolder.clearContext();
                JwtAuthenticationException authenticationException = new JwtAuthenticationException("jwt 인증에 실패했습니다.");
                this.failureHandler.onAuthenticationFailure(request, response, authenticationException);
                throw authenticationException;
            }
            filterChain.doFilter(request, response);
        }
    }

    private void postAuthenticate(HttpServletRequest request, HttpServletResponse response, JwtAuthentication authenticationResult) {
        Boolean isLocal = CookieUtil.getCookie(request, IS_LOCAL)
                .map(Cookie::getValue)
                .map(Boolean::parseBoolean)
                .orElse(false);
        log.info("ISLOCAL: {}", isLocal);
        // authenticationResult 받기 <- 여기에 들어온다는 것 자체가 인증 성공했단 뜻임.
        JwtAuthentication jwtAuthenticationResult = (JwtAuthentication) authenticationResult;

        // 쿠키 만들기
        ResponseCookie accessTokenCookie = CookieUtil.createAccessTokenCookie(jwtAuthenticationResult.getToken().getAccessToken(), isLocal);
        ResponseCookie refreshTokenCookie = CookieUtil.createRefreshTokenCookie(jwtAuthenticationResult.getToken().getRefreshToken(), isLocal);

        // 쿠키 response에 등록하기
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        response.addHeader("Set-Cookie", String.valueOf(isLocal));

    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if(request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
