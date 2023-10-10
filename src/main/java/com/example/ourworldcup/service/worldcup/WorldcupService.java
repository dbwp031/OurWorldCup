package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Invitation;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.MemberRole;
import com.example.ourworldcup.domain.userAccount.UserAccount;

import java.util.List;

public interface WorldcupService {
    Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto, UserAccount userAccount);

    Worldcup findByInvitation(Invitation invitation);

    void enrollUserAccount(Worldcup worldcup, UserAccount userAccount, MemberRole memberRole);

    void enrollUserAccount(Invitation invitation, UserAccount userAccount, MemberRole memberRole);

    Worldcup findById(Long worldcupId);

    List<Integer> getRoundTypes(Long id);

    String getTitle(Long id);
}
