package com.example.ourworldcup.auth.provider;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import com.example.ourworldcup.auth.dto.Token;
import com.example.ourworldcup.auth.dto.UidDto;
import com.example.ourworldcup.auth.dto.UserAccountDto;
import com.example.ourworldcup.auth.exception.JwtAuthenticationException;
import com.example.ourworldcup.auth.service.TokenService;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.service.userAccount.UserAccountService;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Optional;

@Component
public class JwtProvider implements AuthenticationProvider {
    private final UserAccountService userAccountService;
    private final TokenService tokenService;
    private final Key secretKey;
    private final long tokenPeriod;
    private final long refreshPeriod;

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey,
                       @Value("${jwt.token-validity-in-sec}") long tokenPeriod,
                       @Value("${jwt.refresh-token-validity-in-sec}") long refreshPeriod,
                       UserAccountService userAccountService, TokenService tokenService) {

        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(secretByteKey);

        this.userAccountService = userAccountService;
        this.tokenService = tokenService;
        this.tokenPeriod = tokenPeriod * 1000;
        this.refreshPeriod = refreshPeriod * 1000;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // JwtAuthentication을 사용해서 인증함.
        JwtAuthentication jwtAuthenticationToken = (JwtAuthentication) authentication;
        // JwtAuthentication안에 있는 token을 가져옴
        Token token = ((JwtAuthentication) authentication).getToken();
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        // AccessToken에 대해서 확인함.
        // null값 확인
        Optional<String> optionalAccessToken = Optional.ofNullable(accessToken);
        // 상태 확인
        TokenService.JwtCode status = optionalAccessToken.map(
                this::validateToken).orElse(TokenService.JwtCode.EXPIRED);
        if (status.equals(TokenService.JwtCode.ACCESS)) {
            setAuthMetadata(token, jwtAuthenticationToken);
            return jwtAuthenticationToken;
        } else if (status.equals(TokenService.JwtCode.EXPIRED)) {
            // refresh token 가지고 access token 재발급
            if (refreshToken == null) {
                throw new JwtAuthenticationException("토큰 재발급을 위해선 refresh token이 필요합니다.", ErrorCode.JWT_BAD_REQUEST);
            }
            TokenService.JwtCode code = validateToken(refreshToken);
            Token newToken = tokenService.reissueToken(refreshToken, code);
            setAuthMetadata(newToken, jwtAuthenticationToken);
            return jwtAuthenticationToken;
        } else {
            throw new JwtAuthenticationException(ErrorCode.JWT_DENIED);
        }
    }

    private void setAuthMetadata(Token token, JwtAuthentication authentication) {
        // 토큰에서 unique 값들을 가져옴
        UidDto uidDto = tokenService.getUid(token.getAccessToken());
        String email = uidDto.getEmail();
        AuthProviderType authProviderType = uidDto.getAuthProviderType();

        // unique 값으로 유저를 db에서 불러옴
        UserAccount userAccount = userAccountService.findByEmailAndAuthProviderType(email, authProviderType)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_ACCOUNT_NOT_FOUND.getMessage()));
        // userAccountDto 생성
        UserAccountDto userAccountDto = UserAccountDto.builder()
                .email(email)
                .name(userAccount.getUserName())
                .picture(userAccount.getPicture())
                .authProviderType(authProviderType)
                .build();
        // authentication에 메타데이터들을 재설정함.
        authentication.setToken(token);
        authentication.setAuthenticated(true);
        authentication.setPrincipal(userAccountDto);
        authentication.setPrincipalDetails(userAccount);
        authentication.setAuthorities(userAccountService.getAuthorities(userAccount));
    }
    private TokenService.JwtCode validateToken(String token) {
        return this.tokenService.verifyToken(token);
    }
}
