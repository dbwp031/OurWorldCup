package com.example.ourworldcup.exception.handler;

import com.example.ourworldcup.exception.ErrorCode;
import com.example.ourworldcup.exception.GeneralException;

public class UserAccountException extends GeneralException {
    public UserAccountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
