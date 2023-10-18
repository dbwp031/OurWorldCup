package com.example.ourworldcup.service.game.itemSelectionPolicy;

import com.example.ourworldcup.domain.constant.PickType;
import com.example.ourworldcup.service.game.itemSelectionPolicy.impl.OrderItemSelectionPolicy;
import com.example.ourworldcup.service.game.itemSelectionPolicy.impl.RandomItemSelectionPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemSelectionPolicyFactory {
    private final OrderItemSelectionPolicy orderItemSelectionPolicy;
    private final RandomItemSelectionPolicy randomItemSelectionPolicy;

    public ItemSelectionPolicy createItemSelectionPolicy(PickType pickType) {
        ItemSelectionPolicy policy = null;
        if (pickType == PickType.ORDER) {
            policy = orderItemSelectionPolicy;
        } else if (pickType == PickType.RANDOM) {
            policy = randomItemSelectionPolicy;
        }

        return policy;
    }
}
