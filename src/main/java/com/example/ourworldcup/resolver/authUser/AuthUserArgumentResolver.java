package com.example.ourworldcup.resolver.authUser;

import com.example.ourworldcup.auth.dto.UserAccountDto;
import com.example.ourworldcup.converter.userAccount.UserAccountConverter;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
        if (authUser == null) return false;
        if (!parameter.getParameterType().equals(UserAccount.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object principal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("error: 1");
            principal = authentication.getPrincipal();
        }
        if (principal == null || principal.getClass() == String.class) {
            System.out.println("error: 2");
            return null;
        }
        UserAccountDto loginUser = (UserAccountDto) principal;
        return UserAccountConverter.toUserAccount(loginUser);
    }
}
