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
    @Column(nullable = false, length=100) String title;
    @Column(nullable = false, length = 36) private String imageUuid;
    @Column(length=255) String description;
}
