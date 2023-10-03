package com.example.ourworldcup.domain.constant;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public enum PickType {
    ORDER{
        @Override
        public List<Integer> getPickOrder(Long itemsNum) {
            return IntStream.range(0, itemsNum.intValue()).boxed()
                    .collect(Collectors.toList());
        }
    },
    CUSTOMIZED{
        @Override
        public List<Integer> getPickOrder(Long itemsNum) {
            return null;
        }
    },
    RANDOM{
        @Override
        public List<Integer> getPickOrder(Long itemsNum) {
            return null;
        }
    };


    public abstract List<Integer> getPickOrder(Long itemsNum);
}
