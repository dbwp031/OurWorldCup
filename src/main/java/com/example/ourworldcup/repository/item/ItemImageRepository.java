package com.example.ourworldcup.repository.item;

import com.example.ourworldcup.domain.ItemImage;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
