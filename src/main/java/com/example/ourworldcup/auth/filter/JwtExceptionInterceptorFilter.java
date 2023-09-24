package com.example.ourworldcup.auth.filter;

import com.example.ourworldcup.auth.exception.JwtAuthenticationException;
import com.example.ourworldcup.exception.ErrorCode;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.io.IOException;

public class JwtExceptionInterceptorFilter extends ExceptionTranslationFilter {

    public JwtExceptionInterceptorFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.doFilterInternal((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException exception) {
            this.sendStartAuthentication(request, response, filterChain, exception);
        } catch (JwtException exception) {
            JwtAuthenticationException authenticationException = new JwtAuthenticationException(ErrorCode.JWT_SERVER_ERROR.getMessage());
            this.sendStartAuthentication(request, response, filterChain, authenticationException);
        }
    }
}
