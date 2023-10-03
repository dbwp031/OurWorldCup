package com.example.ourworldcup.converter.round;

import com.example.ourworldcup.controller.game.dto.GameResponseDto;
import com.example.ourworldcup.controller.game.dto.RoundResponseDto;
import com.example.ourworldcup.converter.game.GameConverter;
import com.example.ourworldcup.converter.item.ItemConverter;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.service.item.ItemService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class RoundConverter {
    private final ItemService itemService;
    private static ItemService staticItemService;

    @PostConstruct
    public void init() {
        staticItemService = this.itemService;
    }
    public static RoundResponseDto.BeforeRoundDto toRoundResponseBeforeRoundDto(Round round) throws Exception {
        Game game = round.getGame();
        return RoundResponseDto.BeforeRoundDto.builder()
                .id(round.getId())
                .worldcupId(game.getWorldcup().getId())
                .gameId(game.getId())
                .roundNum(round.getRoundNum())
                .game(GameConverter.toGameResponseRoundInfoDto(game))
                .item1(ItemConverter.toItemResponseBasicDto(staticItemService.findById(round.getItem1())))
                .item2(ItemConverter.toItemResponseBasicDto(staticItemService.findById(round.getItem2())))
                .build();
    }

    public static RoundResponseDto.BasicDto toRoundResponseBasicDto(Round round) throws Exception {
        return RoundResponseDto.BasicDto.builder()
                .id(round.getId())
                .worldcupId(round.getWorldcup().getId())
                .gameId(round.getGame().getId())
                .roundNum(round.getRoundNum())
                .item1(ItemConverter.toItemResponseBasicDto(staticItemService.findById(round.getItem1())))
                .item2(ItemConverter.toItemResponseBasicDto(staticItemService.findById(round.getItem2())))
                .selectedItem(ItemConverter.toItemResponseBasicDto(staticItemService.findById(round.getSelectedItem())))
                .build();

    }
}
