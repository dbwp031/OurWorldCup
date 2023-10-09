package com.example.ourworldcup.repository.uuid;

import com.example.ourworldcup.domain.Uuid;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface UuidRepository extends
        JpaRepository<Uuid, Long>,
        UuidCustomRepository {
}
