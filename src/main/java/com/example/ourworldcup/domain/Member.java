package com.example.ourworldcup.domain;

import com.example.ourworldcup.domain.constant.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class Member extends AuditingFields{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name="user_account_id") private UserAccount userAccount;
    @ManyToOne(optional = false) @JoinColumn(name="worldcup_id") private Worldcup worldcup;
    @Column(nullable = false) MemberRole memberRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
