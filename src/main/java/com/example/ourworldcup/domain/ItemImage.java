package com.example.ourworldcup.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class ItemImage extends AuditingFields{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String fileName;
    private String url;
    @OneToOne @JoinColumn(name = "uuid_id") private Uuid uuid;
    @OneToOne @JoinColumn(name = "item_id") private Item item;
}
