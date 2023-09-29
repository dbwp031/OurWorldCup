package com.example.ourworldcup.auth.service;

import com.example.ourworldcup.auth.dto.Token;
import com.example.ourworldcup.auth.dto.UidDto;
import com.example.ourworldcup.auth.exception.JwtAuthenticationException;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.service.userAccount.UserAccountService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {
    public static enum JwtCode {
        DENIED,
        ACCESS,
        EXPIRED;
    }
    private final Key secretKey;
    private final Long tokenPeriod;
    private final Long refreshPeriod;
    private final UserAccountService userAccountService;

    public TokenService(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.token-validity-in-sec}") long tokenPeriod,
            @Value("${jwt.refresh-token-validity-in-sec}") long refreshPeriod,
            UserAccountService userAccountService
    ) {
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(secretByteKey);

        this.tokenPeriod = tokenPeriod * 1000;
        this.refreshPeriod = refreshPeriod * 1000;
        this.userAccountService = userAccountService;
    }

    public Token generateToken(String email, AuthProviderType authProviderType, List<RoleType> roleTypes) {
        return new Token(this.generateAccessToken(email, authProviderType, roleTypes),
                this.generateRefreshToken(email, authProviderType, roleTypes));

    }

    private String generateRefreshToken(String email, AuthProviderType authProviderType, List<RoleType> roleTypes) {
        String subject = email + "," + authProviderType.name();
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("roleTypes", roleTypes);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshPeriod))
                .signWith(secretKey)
                .compact();
    }

    private String generateAccessToken(String email, AuthProviderType authProviderType, List<RoleType> roleTypes) {
        String subject = email + "," + authProviderType.name();
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("roleTypes", roleTypes);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(secretKey)
                .compact();
    }

    public JwtCode verifyToken(String token) {
        try {
            // secretKey를 가지고 claims을 디코딩해서 가져와라.
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build().parseClaimsJws(token);

            // 만료시간이 지금보다 이전(before)이면 true
            if (claims.getBody()
                    .getExpiration()
                    .before(new Date())) {
                throw new ExpiredJwtException(claims.getHeader(), claims.getBody(), "expired Token, reissue refresh Token");
            }
            return JwtCode.ACCESS;
        } catch (ExpiredJwtException e) {
            return JwtCode.EXPIRED;
        } catch (Exception e) {
            return JwtCode.DENIED;
        }
    }

    public UidDto getUid(String token) {
        String subject = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
        String[] subjectContext = subject.split(",");
        return UidDto.builder()
                .email(subjectContext[0])
                .authProviderType(AuthProviderType.valueOf(subjectContext[1]))
                .build();
    }
    @Transactional
    public Token reissueToken(String refreshToken) throws RuntimeException {
        UidDto uidDto = getUid(refreshToken);
        String email = uidDto.getEmail();
        AuthProviderType authProviderType = uidDto.getAuthProviderType();
        UserAccount userAccount = userAccountService.findByEmailAndAuthProviderType(email, authProviderType)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_ACCOUNT_NOT_FOUND.getMessage()));
        // db의 refreshtoken과 비교
        if (userAccount.getRefreshToken().equals(refreshToken)) {
            // 새로운 refresh token 생성
            Token newToken = generateToken(email, authProviderType, userAccountService.getRoleTypes(userAccount));
            userAccount.setRefreshToken(newToken.getRefreshToken());
            return newToken;
        } else {
            throw new JwtAuthenticationException("토큰 재발급에 실패했습니다.", ErrorCode.JWT_BAD_REQUEST);
        }
    }

    @Transactional
    public Token reissueToken(String refreshToken, JwtCode status) {
        UidDto uidDto = getUid(refreshToken);
        String email = uidDto.getEmail();
        AuthProviderType authProviderType = uidDto.getAuthProviderType();
        UserAccount userAccount = userAccountService.findByEmailAndAuthProviderType(email, authProviderType)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_ACCOUNT_NOT_FOUND.getMessage()));

        if (status.equals(JwtCode.ACCESS)) {
            if (!userAccount.equals(email, authProviderType)) {
                throw new JwtAuthenticationException("요청한 refreshToken이 멤버 정보와 일치하지 않습니다.", ErrorCode.JWT_BAD_REQUEST);
            }
            String accessToken = this.generateAccessToken(email, authProviderType, userAccountService.getRoleTypes(userAccount));
            return new Token(accessToken, refreshToken);
        } else if (status.equals(JwtCode.EXPIRED)) {
            if (!userAccount.equals(email, authProviderType)) {
                throw new JwtAuthenticationException("요청한 refreshToken이 멤버 정보와 일치하지 않습니다.", ErrorCode.JWT_BAD_REQUEST);
            }
            Token newToken = this.generateToken(email, authProviderType, userAccountService.getRoleTypes(userAccount));
            userAccount.setRefreshToken(newToken.getRefreshToken());
            return newToken;
        } else {
            throw new JwtAuthenticationException("토큰 재발급에 실패했습니다.", ErrorCode.JWT_BAD_REQUEST);
        }
    }
}


