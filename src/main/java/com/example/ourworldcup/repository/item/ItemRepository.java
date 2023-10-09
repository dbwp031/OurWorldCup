package com.example.ourworldcup.repository.item;

import com.example.ourworldcup.domain.Item;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Hidden
public interface ItemRepository extends
        JpaRepository<Item, Long>,
        ItemCustomRepository {
    @Query("select item from Item item where item.worldcup.id = :worldcupId")
    Optional<Item> findItemByWorldcupId(@Param("worldcupId") Long worldcupId);

    void deleteById(Long id);

}
