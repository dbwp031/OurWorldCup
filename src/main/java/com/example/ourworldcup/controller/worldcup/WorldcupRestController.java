package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.worldcup.WorldcupConverter;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import com.example.ourworldcup.resolver.authUser.AuthUser;
import com.example.ourworldcup.service.game.GameService;
import com.example.ourworldcup.service.item.ItemService;
import com.example.ourworldcup.service.member.MemberService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/worldcup")
@RestController
public class WorldcupRestController {
    private static final String SESSION_ATTR_WORLDCUP = "worldcup";
    private static final String SESSION_ATTR_ITEMS = "items";
    private final WorldcupService worldcupService;
    private final ItemService itemService;
    private final MemberService memberService;
    private final GameService gameService;

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable Long id, HttpSession session, HttpServletResponse response) {
        Worldcup worldcup = (Worldcup) session.getAttribute(SESSION_ATTR_WORLDCUP);

        itemService.deleteItem(id);

        Map<String, Boolean> responseMap = new HashMap<>();
        responseMap.put("success", true);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMember(@PathVariable Long id) {
        memberService.deleteMemberById(id);
        Map<String, Boolean> responseMap = new HashMap<>();
        responseMap.put("success", true);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping("/{worldcupId}/roundTypes")
    public ResponseEntity<WorldcupResponseDto.RoundTypesDto> getRoundTypes(@PathVariable Long worldcupId) {
        Worldcup worldcup = worldcupService.findById(worldcupId);
        return ResponseEntity.ok(WorldcupConverter.toWorldcupResponseRoundTypesDto(worldcup));
    }

    @PostMapping("/{worldcupId}/game")
    public ResponseEntity<Map<String, Long>> createGame(@PathVariable Long worldcupId, @RequestParam Long initRound, @AuthUser UserAccount userAccount) {
        Game game = gameService.createGame(userAccount.getId(), worldcupId, initRound, PickType.ORDER);
        Map<String, Long> responseMap = new HashMap<>();
        responseMap.put("gameId", game.getId());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
