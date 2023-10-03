package com.example.ourworldcup.repository.item;

public interface ItemCustomRepository {
    boolean checkExistByItemTitleInSameWorldcup(Long worldcupId, String title);
}
