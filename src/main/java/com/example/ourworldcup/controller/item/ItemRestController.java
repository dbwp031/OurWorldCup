package com.example.ourworldcup.controller.item;

import com.example.ourworldcup.service.item.ItemService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
@RestController
public class ItemRestController {
    private final ItemService itemService;
}
