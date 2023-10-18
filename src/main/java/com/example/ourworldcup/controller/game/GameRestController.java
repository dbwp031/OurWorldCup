package com.example.ourworldcup.controller.game;

import com.example.ourworldcup.controller.game.dto.RoundRequestDto;
import com.example.ourworldcup.controller.game.dto.RoundResponseDto;
import com.example.ourworldcup.converter.round.RoundConverter;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.service.round.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/game")
@RestController
public class GameRestController {
    private final RoundService roundService;

    @GetMapping("/{gameId}/round/{roundNum}")
    public ResponseEntity<RoundResponseDto.BeforeRoundDto> getRound(@PathVariable Long gameId,
                                                                    @PathVariable Long roundNum) throws Exception {
        roundService.setCurrentRoundOrder(gameId, roundNum);
        roundService.updateGameIfStageEnded(gameId);

        Round round = roundService.getRound(gameId);

        return new ResponseEntity<>(RoundConverter.toRoundResponseBeforeRoundDto(round), HttpStatus.OK);
    }

    @PostMapping("/{gameId}/round/{roundNum}")
    public ResponseEntity<Map<String, Boolean>> saveRound(@PathVariable Long gameId,
                                                          @PathVariable Long roundNum,
                                                          @RequestBody RoundRequestDto.RoundResultDto roundResultDto) {

        roundService.saveRoundResult(roundResultDto);

        Map<String, Boolean> responseMap = new HashMap<>();
        Boolean isFinished = roundService.checkFinished(gameId);

        responseMap.put("success", true);
        responseMap.put("isFinished", isFinished);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
