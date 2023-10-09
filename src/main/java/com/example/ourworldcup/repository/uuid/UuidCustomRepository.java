package com.example.ourworldcup.repository.uuid;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public interface UuidCustomRepository {
    public Boolean exist(String uuid);
}
