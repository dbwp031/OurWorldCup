package com.example.ourworldcup.controller.invitation;

import com.example.ourworldcup.service.invitation.InvitationService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/invi")
@Controller
public class InvitationController {
    private final InvitationService invitationService;
    private final WorldcupService worldcupService;

    @GetMapping("/join")
    public String renderInvitation() {
        return "invitation/v1/main";
    }

}
