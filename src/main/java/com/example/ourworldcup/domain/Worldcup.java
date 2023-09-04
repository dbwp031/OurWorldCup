package com.example.ourworldcup.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Builder
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class Worldcup extends AuditingFields{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String password;
    @Column(nullable = false, length=100) private String title;
    @Column(length = 10000) private String content;
    @Column(nullable=false, length=255) private String invitationCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worldcup worldcup = (Worldcup) o;
        return Objects.equals(id, worldcup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
