package com.example.ourworldcup.controller.invitation;

import com.example.ourworldcup.auth.AuthUser;
import com.example.ourworldcup.controller.invitation.dto.InvitationResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.invitation.InvitationConverter;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.Invitation;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.MemberRole;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.service.invitation.InvitationService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import com.example.ourworldcup.validator.inviToken.ExistInviToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "01-Invitation 💌", description = "월드컵 초대 확인")
@RequestMapping("/api/v1/invi")
@RestController
public class InvitationRestController {
    private final InvitationService invitationService;
    private final WorldcupService worldcupService;

    @Operation(summary = "초대 토큰 유효성 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Parameters({
            @Parameter(name = "token", description = "월드컵 초대 토큰", example = "{base64encoding된 uuid}")
    })
    @GetMapping("")
    public ResponseEntity<InvitationResponseDto.ValidityDto> validInviToken(@RequestParam @ExistInviToken String token) {
        //TODO: INVITATION 토큰 만료를 테스트하지 않고 있다.
        Invitation invitation = invitationService.findByUuid(token);
        return ResponseEntity.ok(InvitationConverter.toInvitationResponseValidityDto(invitation));
    }

    @Operation(summary = "월드컵에 사용자 참가 요청 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Parameters({
            @Parameter(name = "token", description = "월드컵 초대 토큰", example = "{base64encoding된 uuid}")
    })
    @PostMapping("/join")
    public ResponseEntity<WorldcupResponseDto.PreviewDto> joinWorldcup(@RequestParam @ExistInviToken String token,
                                                                       @AuthUser UserAccount userAccount) {
        
        System.out.println(userAccount.toString());
        Invitation invitation = invitationService.findByUuid(token);
        Worldcup worldcup = worldcupService.findByInvitation(invitation);
        worldcupService.enrollUserAccount(worldcup, userAccount, MemberRole.PARTICIPANT);
        return ResponseEntity.ok(WorldcupConverter.toWorldcupResponsePreviewDto(worldcup));
    }
//
//
//    @GetMapping("/participate")
//    public String participateWorldcup(@RequestParam String token, Authentication authentication) {
//
//        // 1. 유효한 Invitation Uuid인지 확인
//        Optional<Invitation> invitation = invitationService.findByUuid(token);
//        // 2. 만료되지 않은 Invitation인지 확인
//        if (invitation.isPresent() && !invitationService.isExpired(invitation.get())) {
//            Worldcup worldcup = invitation.get().getWorldcup();
//            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
//            worldcupService.enrollUserAccount(worldcup, jwtAuthentication.getPrincipalDetails(), MemberRole.PARTICIPANT);
//            return "redirect:/worldcup/" + worldcup.getId() + "/details";
//        }
//        return "invitation/error";
//    }
}
