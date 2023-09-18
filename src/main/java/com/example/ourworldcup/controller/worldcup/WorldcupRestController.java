package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.service.item.ItemService;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable Long id, HttpSession session, HttpServletResponse response) {
        Worldcup worldcup = (Worldcup) session.getAttribute(SESSION_ATTR_WORLDCUP);

        itemService.deleteItem(id);

        Map<String, Boolean> responseMap = new HashMap<>();
        responseMap.put("success", true);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
