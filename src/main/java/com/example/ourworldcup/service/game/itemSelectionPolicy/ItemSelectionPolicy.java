package com.example.ourworldcup.service.game.itemSelectionPolicy;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.match.Round;

import java.util.List;

public interface ItemSelectionPolicy {
    List<Round> createRounds(List<Item> items);

}
