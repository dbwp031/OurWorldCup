package com.example.ourworldcup.controller.game;

import com.example.ourworldcup.converter.game.GameConverter;
import com.example.ourworldcup.converter.item.ItemConverter;
import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.service.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/game")
@Controller
public class GameController {
    private final GameService gameService;

    @GetMapping("/{gameId}")
    public String renderGamePage(@PathVariable Long gameId, ModelMap modelMap) {
        return "game/ingame";
    }

    @GetMapping("/{gameId}/result")
    public String renderResultPage(@PathVariable Long gameId, ModelMap modelMap) throws Exception {
        Item item = gameService.getWinnerItem(gameId);
        System.out.println("winner!!!!");
        System.out.println(item.toString());
        modelMap.addAttribute("winnerItem", ItemConverter.toItemResponseBasicDto(item));
        return "game/result";
    }
}
