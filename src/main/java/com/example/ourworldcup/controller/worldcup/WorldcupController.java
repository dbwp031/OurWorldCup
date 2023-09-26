package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.auth.authentication.JwtAuthentication;
import com.example.ourworldcup.aws.s3.FileService;
import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupResponseDto;
import com.example.ourworldcup.converter.item.ItemConverter;
import com.example.ourworldcup.domain.Worldcup;
import com.example.ourworldcup.service.worldcup.WorldcupService;
import com.example.ourworldcup.service.item.ItemService;
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
public class    WorldcupController {
    private static final String SESSION_ATTR_WORLDCUP = "worldcup";
    private static final String SESSION_ATTR_ITEMS = "items";

    private final ItemService itemService;
    private final WorldcupService worldcupService;
    private final ItemConverter itemConverter;
    private final FileService fileService;
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

        Worldcup worldcup = (Worldcup) httpSession.getAttribute(SESSION_ATTR_WORLDCUP);
        WorldcupResponseDto.WorldcupItemsDto worldcupItemsDto = new WorldcupResponseDto.WorldcupItemsDto();
        List<WorldcupResponseDto.WorldcupItemDto> itemsDto = worldcup.getItems().stream().map(a -> {
            try {
                return itemConverter.toWorldcupItemDto(a, fileService);
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
    public String worldcupInvite(ModelMap modelMap, Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        modelMap.addAttribute("userId", jwtAuthentication.getPrincipalDetails().getUserId());

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

}
