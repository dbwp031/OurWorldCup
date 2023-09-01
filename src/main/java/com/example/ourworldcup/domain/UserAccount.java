package com.example.ourworldcup.domain;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.Objects;

@ToString(callSuper = true)
@Entity
public class UserAccount extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) private String userId;
    @Column(nullable = false, length = 100) private String username;
    @Column(length = 100) private String nickname;
    @Column(length = 100) private String email;

    private UserAccount(){}

    private UserAccount(String userId, String username, String nickname, String email) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }

    public static UserAccount of(String userId, String username, String nickname, String email) {
        return new UserAccount(userId, username, nickname, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
