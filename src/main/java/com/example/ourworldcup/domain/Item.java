package com.example.ourworldcup.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class Item extends AuditingFields{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "worldcup_id") private Worldcup worldcup;
    @Setter @OneToOne @JoinColumn(name = "item_image_id") private ItemImage itemImage;
    @Column(nullable = false, length=100) private String title;
}
