package com.example.ourworldcup.repository.uuid;

import com.example.ourworldcup.domain.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends
        JpaRepository<Uuid, Long>,
        UuidCustomRepository {
}
