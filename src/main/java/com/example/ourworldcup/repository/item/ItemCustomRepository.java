package com.example.ourworldcup.repository.item;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface ItemCustomRepository {
    boolean checkExistByItemTitleInSameWorldcup(Long worldcupId, String title);
}
