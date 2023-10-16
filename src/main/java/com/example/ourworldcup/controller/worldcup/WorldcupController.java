package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.game.dto.GameResponseDto;
import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.game.GameConverter;
import com.example.ourworldcup.converter.item.ItemConverter;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.dto.VsResultDto;
import com.example.ourworldcup.repository.WorldcupRepository;
import com.example.ourworldcup.service.game.GameService;
import com.example.ourworldcup.service.item.ItemService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/worldcup")
@Controller
public class WorldcupController {
    private static final String SESSION_ATTR_WORLDCUP = "worldcup";
    private static final String SESSION_ATTR_ITEMS = "items";

    private final ItemService itemService;
    private final WorldcupService worldcupService;
    private final ItemConverter itemConverter;
    private final FileService fileService;
    private final WorldcupRepository worldcupRepository;
    private final GameService gameService;

    // worldcup 생성 페이지 - GetMapping
    @GetMapping("/new")
    public String worldcupAdd() {
        return "forward:/worldcup/new/worldcup-info";
    }

    @GetMapping("/new/worldcup-info")
    public String renderWorldcupInfoCreationForm() {
        return "worldcup/new/worldcup_info";
    }

    @GetMapping("/new/add-item")
    public String renderWorldcupAddItemCreationForm() {
        return "worldcup/new/add_item";
    }

    @GetMapping("/new/items")
    public String renderWorldcupItemsCreationForm(ModelMap map, HttpSession httpSession) throws Exception {
        Worldcup sessionWorldcup = (Worldcup) httpSession.getAttribute(SESSION_ATTR_WORLDCUP);
        Worldcup worldcup = worldcupService.findById(sessionWorldcup.getId());
        WorldcupResponseDto.WorldcupItemsDto worldcupItemsDto = new WorldcupResponseDto.WorldcupItemsDto();
        List<WorldcupResponseDto.WorldcupItemDto> itemsDto = worldcup.getItems().stream().map(a -> {
            try {
                return itemConverter.toWorldcupItemDto(a);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        worldcupItemsDto.setItems(itemsDto);
        map.addAttribute("worldcupItemsDto", worldcupItemsDto);
        return "worldcup/new/items";
    }

    //
    @GetMapping("/new/invite")
    public String worldcupInvite(ModelMap modelMap, Authentication authentication, HttpSession httpSession) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        Worldcup worldcup = (Worldcup) httpSession.getAttribute(SESSION_ATTR_WORLDCUP);
        WorldcupResponseDto.BasicDto worldcupResponseBasicDto = WorldcupConverter.toWorldcupResponseBasicDto(worldcup);
        modelMap.addAttribute("userId", jwtAuthentication.getPrincipalDetails().getUserId());
        modelMap.addAttribute("worldcupDto", worldcupResponseBasicDto);
        return "worldcup/new/invite";
    }

//    @GetMapping("/new/invite")
//    public String renderWorldcupInvitationCreationForm() {
//        return "/worldcup/new/invite";
//    }

    // worldcup 생성 페이지 - PostMapping
    @PostMapping("/new/worldcup-info")
    public String saveWorldcupInfo_redirectWorldcupAddItemCreationForm(
            @ModelAttribute WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto,
            Authentication authentication,
            HttpSession httpSession) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        Worldcup worldcup = worldcupService.createWorldcup(worldcupCreateRequestDto, jwtAuthentication.getPrincipalDetails());
        httpSession.setAttribute(SESSION_ATTR_WORLDCUP, worldcup);
        return "redirect:/worldcup/new/add-item";
    }

    @PostMapping("/new/add-item")
    public String doNothing_redirectWorldcupItemCreationForm() {
        return "redirect:/worldcup/new/items";
    }

    @PostMapping("/new/item")
    public String saveImageItems_redirectWorldcupItemCreationForm(
            @ModelAttribute ItemRequestDto.ItemCreateRequestDto itemCreateRequestDto,
            HttpSession httpSession
    ) {
        itemService.saveItem(itemCreateRequestDto,
                (Worldcup) httpSession.getAttribute(SESSION_ATTR_WORLDCUP));
        return "redirect:/worldcup/new/add-item";
    }

    @GetMapping("/{worldcupId}/details")
    public String renderWorldcupDetails(@PathVariable Long worldcupId,
                                        ModelMap modelMap) {
        Worldcup worldcup = worldcupService.findById(worldcupId);
        WorldcupResponseDto.BasicDto worldcupResponseBaseDto = WorldcupConverter.toWorldcupResponseBasicDto(worldcup);
        modelMap.addAttribute("worldcupDto", worldcupResponseBaseDto);
        return "worldcup/detail";
    }

    @GetMapping("/{worldcupId}/members")
    public String renderWorldcupMembers(@PathVariable Long worldcupId,
                                        ModelMap modelMap) {
        Worldcup worldcup = worldcupService.findById(worldcupId);
        WorldcupResponseDto.MembersDto worldcupResponseMembersDto = WorldcupConverter.toWorldcupResponseMembersDto(worldcup);
        modelMap.addAttribute("worldcupDto", worldcupResponseMembersDto);
        return "worldcup/members";
    }

    /*
     * 특정 월드컵에 대한 게임을 만드는 것이기 때문에 Worldcup - Game의 계층관계가 명확하다.
     * 이렇게 명확할 때에는 path variable을 사용한다.
     *
     * 그렇지 않을 경우엔 query paramter을 사용할 수 있다.
     * */
    @GetMapping("/{worldcupId}/game/new")
    public String renderGameSettingPage() {
        return "game/setting";
    }

    @GetMapping("/{worldcupId}/game/new/round/{round}")
    public String createGame(@PathVariable Long worldcupId,
                             @PathVariable(value = "round") Long initialRound,
                             Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        Game game = gameService.createGame(jwtAuthentication.getPrincipalDetails().getId(), worldcupId, initialRound, PickType.ORDER);
        return String.format("redirect:/game/%d", game.getId());
    }

    @GetMapping("/{worldcupId}/games")
    public String renderGamesPage(@PathVariable Long worldcupId,
                                  ModelMap modelMap) {
        WorldcupResponseDto.GamesDto gamesDto = WorldcupConverter.toWorldcupResponseGamesDto(worldcupService.findById(worldcupId));
        modelMap.addAttribute("gamesDto", gamesDto);
        return "worldcup/games";
    }

    @GetMapping("/{worldcupId}/game/{gameId}/details")
    public String renderGameDetailPage(@PathVariable Long worldcupId,
                                       @PathVariable Long gameId,
                                       ModelMap modelMap) {
        GameResponseDto.ResultDto gameDto = GameConverter.toGameResponseResultDto(gameService.findById(gameId));
        modelMap.addAttribute("gameDto", gameDto);
        return "game/detail";
    }

    @GetMapping("/{worldcupId}/games/vs")
    public String renderGameVsPage(@PathVariable Long worldcupId,
                                   ModelMap modelMap,
                                   Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        List<Game> games = gameService.findGamesByUserAccountId(jwtAuthentication.getPrincipalDetails().getId());
        if (games.size() == 0) {
            return "redirect:/";
        } else if (games.size() == 1) {
            return "redirect:/worldcup/" + worldcupId + "/games/vs/" + games.get(0).getId();
        }
        List<GameResponseDto.ResultDto> gamesDto = games.stream().map(GameConverter::toGameResponseResultDto).toList();
        modelMap.addAttribute("gamesDto", gamesDto);
        return "game/vs/enterGate";
    }

    @GetMapping("/{worldcupId}/games/vs/{gameId}")
    public String renderVsPage(@PathVariable Long worldcupId,
                               @PathVariable Long gameId,
                               ModelMap modelMap) {
        List<VsResultDto> vsResultDtos = gameService.getVsResults(gameId);
        modelMap.addAttribute("vsResultDtos", vsResultDtos);
        modelMap.addAttribute("worldcupId", worldcupId);
        return "game/vs/vs";
    }
}
