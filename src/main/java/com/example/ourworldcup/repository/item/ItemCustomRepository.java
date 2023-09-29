package com.example.ourworldcup.repository.item;

import com.example.ourworldcup.domain.Worldcup;

public interface ItemCustomRepository {
    boolean checkExistByItemTitleInSameWorldcup(Long worldcupId, String title);
}
