package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.auth.dto.UserAccountDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.MemberRole;
import com.example.ourworldcup.domain.userAccount.UserAccount;

public interface WorldcupService {
    Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto, UserAccount userAccount);

    void enrollUserAccount(Worldcup worldcup, UserAccount userAccount, MemberRole memberRole);
}
