package com.example.ourworldcup.service.game.itemSelectionPolicy.impl;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.service.game.itemSelectionPolicy.ItemSelectionPolicy;

import java.util.List;

public class RandomItemSelectionPolicy implements ItemSelectionPolicy {
    @Override
    public List<Round> createRounds(List<Item> items) {
        return null;
    }
}
