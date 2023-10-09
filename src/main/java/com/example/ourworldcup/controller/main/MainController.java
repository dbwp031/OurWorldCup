package com.example.ourworldcup.controller.main;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("")
@Controller
public class MainController {
    @GetMapping("/")
    public String home(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        if (jwtAuthentication != null && jwtAuthentication.isAuthenticated()) {
            return "redirect:/@" + jwtAuthentication.getPrincipalDetails().getUserId();
        }
        return "main/home";
    }

    @GetMapping("/home")
    public String userHome(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        if (jwtAuthentication.isAuthenticated()) {
            return "redirect:/@" + jwtAuthentication.getPrincipalDetails().getUserId();
        }
        return "redirect:/";
    }
}
