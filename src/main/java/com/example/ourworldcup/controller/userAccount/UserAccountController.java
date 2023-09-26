package com.example.ourworldcup.controller.userAccount;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import com.example.ourworldcup.controller.userAccount.dto.UserAccountResponseDto;
import com.example.ourworldcup.converter.userAccount.UserAccountConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@RequestMapping("")
@Controller
public class UserAccountController {
    @GetMapping("/@{userId}")
    public String renderUserPage(@PathVariable String userId,
                                 ModelMap modelMap,
                                 Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        if (! jwtAuthentication.getPrincipalDetails().getUserId().equals(userId)) {
            return "redirect:/";
        }

        UserAccountResponseDto.WithWorldcupsDto userAccountResponseDto = UserAccountConverter.toUserAccountResponseWithWorldcupsDto(
                jwtAuthentication.getPrincipalDetails());
        modelMap.addAttribute("userAccountDto", userAccountResponseDto);

        // 여기서 userId를 사용하여 필요한 작업을 수행하세요.
        // 예를 들어, DB에서 사용자 정보를 가져올 수 있습니다.
        return "userAccount/main";
    }
}
