package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import com.example.ourworldcup.service.item.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RequestMapping("/worldcup")
@Controller
public class WorldcupController {
    private static final String SESSION_ATTR_WORLDCUP = "worldcup";
    private static final String SESSION_ATTR_ITEMS = "items";

    private final ItemService itemService;
    private final WorldcupService worldcupService;
    // worldcup 생성 페이지 - GetMapping
    @GetMapping("/new")
    public String worldcupAdd() {
        return "forward:/worldcup/new/worldcup-info";
    }

    @GetMapping("/new/worldcup-info")
    public String renderWorldcupInfoCreationForm() {
        return "/worldcup/new/worldcup_info";
    }

    @GetMapping("/new/add-item")
    public String renderWorldcupAddItemCreationForm() {
        return "/worldcup/new/add_item";
    }

    @GetMapping("/new/items")
    public String renderWorldcupItemsCreationForm(ModelMap map, HttpSession httpSession) {
        map.addAttribute("worldcupItemsDto", worldcupService.toWorldcupItemsDto((Worldcup) httpSession.getAttribute(SESSION_ATTR_WORLDCUP)));
        return "/worldcup/new/items";
    }

    @GetMapping("/new/invite")
    public String renderWorldcupInvitationCreationForm() {
        return "/worldcup/new/invite";
    }

    // worldcup 생성 페이지 - PostMapping
    @PostMapping("/new/worldcup-info")
    public String saveWorldcupInfo_redirectWorldcupAddItemCreationForm(
            @ModelAttribute WorldcupRequestDto.WorldcupCreateRequestDto worldcupCreateRequestDto,
            HttpSession httpSession) {
        Worldcup worldcup = worldcupService.createWorldcup(worldcupCreateRequestDto);
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
//
//    @PostMapping("/new/invite")
//    public String worldcupInvite() {
//        return "redirect:/worldcup/new/invite";
//    }
}
