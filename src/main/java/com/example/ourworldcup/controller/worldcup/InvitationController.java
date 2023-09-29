package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.Invitation;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.MemberRole;
import com.example.ourworldcup.service.invitation.InvitationService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/worldcup")
@Controller
public class InvitationController {
    private final InvitationService invitationService;
    private final WorldcupService worldcupService;

    @GetMapping("/join")
    public String getInvitationUrl(@RequestParam String token, ModelMap modelMap) {
        // 1. 유효한 Invitation Uuid인지 확인
        Optional<Invitation> invitation = invitationService.findByUuid(token);
        // 2. 만료되지 않은 Invitation인지 확인
        if (invitation.isPresent() && !invitationService.isExpired(invitation.get())) {
            Worldcup worldcup = invitation.get().getWorldcup();
            WorldcupResponseDto.InvitationDto worldcupDto = WorldcupConverter.toWorldcupResponseInvitationDto(worldcup);
            modelMap.addAttribute("worldcupDto", worldcupDto);
            modelMap.addAttribute("token", token);
            return "invitation/correct";
        }
        return "invitation/error";
        // 2. 월드컵 참여하겠는지 물어보는 페이지 리턴
    }

    @GetMapping("/participate")
    public String participateWorldcup(@RequestParam String token, Authentication authentication) {
        // 1. 유효한 Invitation Uuid인지 확인
        Optional<Invitation> invitation = invitationService.findByUuid(token);
        // 2. 만료되지 않은 Invitation인지 확인
        if (invitation.isPresent() && !invitationService.isExpired(invitation.get())) {
            Worldcup worldcup = invitation.get().getWorldcup();
            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
            worldcupService.enrollUserAccount(worldcup, jwtAuthentication.getPrincipalDetails(), MemberRole.PARTICIPANT);
            return "redirect:/worldcup/" + worldcup.getId() + "/details";
        }
        return "invitation/error";
    }

}
