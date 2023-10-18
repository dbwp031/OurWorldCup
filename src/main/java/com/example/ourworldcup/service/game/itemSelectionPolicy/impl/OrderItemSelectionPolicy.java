package com.example.ourworldcup.service.game.itemSelectionPolicy.impl;

import com.example.ourworldcup.domain.Item;
import com.example.ourworldcup.domain.constant.RoundType;
import com.example.ourworldcup.domain.game.Game;
import com.example.ourworldcup.domain.match.Round;
import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.exception.handler.GameException;
import com.example.ourworldcup.service.game.itemSelectionPolicy.ItemSelectionPolicy;

import java.util.List;

public class OrderItemSelectionPolicy implements ItemSelectionPolicy {
    @Override
    public List<Round> createRounds(List<Item> items) {
        if (items.size() % 2 != 0) {
            throw new GameException(ErrorCode.SELECTION_DENIED);
        }
        List<Round> result = null;
        Item left = null;
        Item right = null;
        for (int i = 0; i < items.size(); i++) {
            if(i%2==0) left = items.get(i);
            if(i%2==1) right = items.get(i);
            if (left != null && right != null) {
                Round.builder()
                        .item1(left.getId())
                        .item2(right.getId())
                        .roundNum()
            }
        }
    }
}
