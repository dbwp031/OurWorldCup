package com.example.ourworldcup.repository;

import com.example.ourworldcup.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
