package com.example.ourworldcup.auth.authentication;

import com.example.ourworldcup.auth.dto.Token;
import com.example.ourworldcup.auth.dto.UserAccountDto;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class JwtAuthentication implements Authentication {

    private Collection<GrantedAuthority> authorities;
    private UserAccountDto principal;
    private UserAccount principalDetails;
    private Token token;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private Boolean isAuthenticated;
    private Object details;

    public void setDetails(Object authenticationDetails) {
        this.details = authenticationDetails;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = (Collection<GrantedAuthority>) authorities;
    }

    public JwtAuthentication(Collection<? extends GrantedAuthority> authorities, UserAccountDto principal, UserAccount details,
                             String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        setAuthenticated(true);
        this.token = new Token(null, refreshToken);
        this.principal = principal;
        this.principalDetails = details;
        this.authorities = (Collection<GrantedAuthority>) authorities;
    }
    public JwtAuthentication(Token token, HttpServletRequest request, HttpServletResponse response) {
        this.token = token;
        this.request = request;
        this.response = response;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
