package com.example.ourworldcup.domain;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.Objects;

@ToString(callSuper = true)
@Entity
public class Worldcup extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=100) private String title;
    @Column(length = 10000) private String content;

    private Worldcup() {}
    private Worldcup(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Worldcup of(String title, String content) {
        return new Worldcup(title, content);
    }

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
