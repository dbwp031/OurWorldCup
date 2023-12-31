package com.example.ourworldcup.service.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.MemberRole;
import com.example.ourworldcup.domain.userAccount.UserAccount;

import java.util.List;
import java.util.Optional;

public interface WorldcupService {
    Worldcup createWorldcup(WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto, UserAccount userAccount);

    void enrollUserAccount(Worldcup worldcup, UserAccount userAccount, MemberRole memberRole);

    Optional<Worldcup> findById(Long worldcupId);

    List<Integer> getRoundTypes(Long id);

    String getTitle(Long id);
}
