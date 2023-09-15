package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/worldcup")
@RestController
public class WorldcupRestController {
    private static final String SESSION_ATTR_WORLDCUP = "worldcup";
    private static final String SESSION_ATTR_ITEMS = "items";
    private final WorldcupService worldcupService;
    @DeleteMapping("/item/{id}")
    public void deleteItem(@PathVariable Long id, HttpSession session, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Worldcup worldcup = (Worldcup) session.getAttribute(SESSION_ATTR_WORLDCUP);

        worldcupService.deleteItem(worldcup.getId(), id);
        response.setStatus(HttpStatus.OK.value());
    }
}
