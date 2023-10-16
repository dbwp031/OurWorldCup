package com.example.ourworldcup.converter.worldcup;

import com.example.ourworldcup.controller.game.dto.GameResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.MemberResponseDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.game.GameConverter;
import com.example.ourworldcup.converter.members.MemberConverter;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.service.game.GameService;
import com.example.ourworldcup.service.invitation.InvitationService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WorldcupConverter {
    private final InvitationService invitationService;
    private final GameService gameService;
    private final WorldcupService worldcupService;
    private static InvitationService staticInvitationService;
    private static GameService staticGameService;
    private static WorldcupService staticWorldcupService;

    @PostConstruct
    public void init() {
        staticInvitationService = invitationService;
        staticGameService = gameService;
        staticWorldcupService = worldcupService;
    }

    public static WorldcupResponseDto.BasicDto toWorldcupResponseBasicDto(Worldcup worldcup) {
        String invitationCode = staticInvitationService.getInvitationUrl(worldcup.getInvitation());

        return WorldcupResponseDto.BasicDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .invitationCode(invitationCode)
                .password(worldcup.getPassword())
                .build();
    }

    public static WorldcupResponseDto.PreviewDto toWorldcupResponsePreviewDto(Worldcup worldcup) {
        return WorldcupResponseDto.PreviewDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .build();
    }

    public static WorldcupResponseDto.MembersDto toWorldcupResponseMembersDto(Worldcup worldcup) {
        List<MemberResponseDto.BasicDto> members = worldcup.getMembers().stream()
                .map(MemberConverter::toMemberResponseBasicDto)
                .toList();
        return WorldcupResponseDto.MembersDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .members(members)
                .build();
    }

    public static WorldcupResponseDto.GamesDto toWorldcupResponseGamesDto(Worldcup worldcup) {
        List<GameResponseDto.ResultDto> games = staticGameService.findGamesByWorldcupId(worldcup.getId()).stream()
                .filter(g -> g.getCurrentRoundType().equals(RoundType.ROUND2) && g.getCurrentRoundOrder().equals(g.getNextStageEndRoundOrder()))
                .map(GameConverter::toGameResponseResultDto)
                .toList();
        return WorldcupResponseDto.GamesDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .games(games)
                .build();
    }

    public static WorldcupResponseDto.RoundTypesDto toWorldcupResponseRoundTypesDto(Worldcup worldcup) {
        List<Integer> roundTypes = staticWorldcupService.getRoundTypes(worldcup.getId());

        return WorldcupResponseDto.RoundTypesDto.builder()
                .id(worldcup.getId())
                .title(worldcup.getTitle())
                .content(worldcup.getContent())
                .roundTypes(roundTypes)
                .build();
    }
}
