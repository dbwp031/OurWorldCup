package com.example.ourworldcup.exception.handler;

import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.exception.GeneralException;

public class ItemException extends GeneralException {
    public ItemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
