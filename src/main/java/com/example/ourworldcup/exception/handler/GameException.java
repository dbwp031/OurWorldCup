package com.example.ourworldcup.exception.handler;

import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.exception.GeneralException;

public class GameException extends GeneralException {
    public GameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
