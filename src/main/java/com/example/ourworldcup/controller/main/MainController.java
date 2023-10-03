package com.example.ourworldcup.controller.main;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("")
@Controller
public class MainController {
    @GetMapping("/")
    public String home(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        if (jwtAuthentication!=null && jwtAuthentication.isAuthenticated()) {
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


//    @GetMapping("/{userId}")
//    public String userHome(HttpSession httpSession, @PathVariable String userId) {
//        log.info("/{userId}에 접근하였습니다.");
//        //TODO: userId가 자신의 아이디인지, 존재하는지 확인해야 한다.
//        if (!httpSession.getAttribute("userId").equals(userId)) {
//            throw new RuntimeException("유저 아이디가 올바르지 않습니다.");
//        }
//        return "main/user";
//    }
}
