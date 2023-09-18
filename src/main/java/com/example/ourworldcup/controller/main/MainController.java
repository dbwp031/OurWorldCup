package com.example.ourworldcup.controller.main;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class MainController {
    @GetMapping("/{userId}")
    public String userHome(HttpSession httpSession, @PathVariable String userId) {
        //TODO: userId가 자신의 아이디인지, 존재하는지 확인해야 한다.
        if (!httpSession.getAttribute("userId").equals(userId)) {
            throw new RuntimeException("유저 아이디가 올바르지 않습니다.");
        }
        return "/main/after_login";
    }
}
