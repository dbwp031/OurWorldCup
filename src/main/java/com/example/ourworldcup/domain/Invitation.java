package com.example.ourworldcup.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString(callSuper = true)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Invitation extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Worldcup worldcup;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    Uuid uuid;
}
