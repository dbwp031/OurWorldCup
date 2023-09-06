package com.example.ourworldcup.controller.worldcup;

import com.example.ourworldcup.controller.item.dto.ItemDto;
import com.example.ourworldcup.controller.item.dto.ItemRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupInfoRequestDto;
import com.example.ourworldcup.controller.worldcup.dto.WorldcupRequestDto;
import com.example.ourworldcup.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/worldcup")
@Controller
public class WorldcupController {
    private static final String SESSION_ATTR_WORLDCUPINFO = "worldcupCreateRequestDto";
    private static final String SESSION_ATTR_ITEMS = "items";

    private final ItemService itemService;
    // worldcup 생성 페이지 - GetMapping
    @GetMapping("/new")

    public String worldcupAdd(HttpSession httpSession) {
        httpSession.setAttribute(SESSION_ATTR_WORLDCUPINFO, null);
        httpSession.setAttribute(SESSION_ATTR_ITEMS, new ArrayList<ItemDto>());
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
    public String renderWorldcupItemsCreationForm() {
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
        httpSession.setAttribute("worldcupCreateRequestDto", worldcupCreateRequestDto);
        return "redirect:/worldcup/new/add-item";
    }

    @PostMapping("/new/add-item")
    public String doNothing_redirectWorldcupItemCreationForm() {
        return "redirect:/worldcup/new/items";
    }

    @PostMapping("/new/image-item")
    public String saveImageItems_redirectWorldcupItemCreationForm(
            @ModelAttribute ItemRequestDto.ImageItemsCreateRequestDto imagesDto,
            HttpSession httpSession
    ) {
        List<ItemDto> items = (List<ItemDto>) httpSession.getAttribute(SESSION_ATTR_ITEMS);
        System.out.println(items.toString());
        for (MultipartFile image : imagesDto.getImages()) {
            items.add(ItemDto.builder()
                    .image(image)
                    .build());
        }
        return "redirect:/worldcup/new/add-item";
    }

    @PostMapping("/new/text-item")
    public String saveText_redirectWorldcupItemCreationForm(
            @ModelAttribute ItemRequestDto.TextItemCreateRequestDto textDto,
            HttpSession httpSession
    ) {
        List<ItemDto> items = (List<ItemDto>) httpSession.getAttribute(SESSION_ATTR_ITEMS);
        System.out.println(items.toString());
        items.add(ItemDto.builder()
                .title(textDto.getTitle())
                .build());
        return "redirect:/worldcup/new/add-item";
    }

//
//    @PostMapping("/new/invite")
//    public String worldcupInvite() {
//        return "redirect:/worldcup/new/invite";
//    }
}
