package com.example.ourworldcup.domain;

import com.example.ourworldcup.domain.constant.MemberRole;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.Objects;

@ToString
@Entity
public class Member extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name="user_account_id") private UserAccount userAccount;
    @ManyToOne(optional = false) @JoinColumn(name="worldcup_id") private Worldcup worldcup;
    @Column(nullable = false) MemberRole memberRole;

    private Member(){}
    private Member(UserAccount userAccount, Worldcup worldcup, MemberRole memberRole) {
        this.userAccount = userAccount;
        this.worldcup = worldcup;
        this.memberRole = memberRole;
    }

    public static Member of(UserAccount userAccount, Worldcup worldcup, MemberRole memberRole) {
        return new Member(userAccount, worldcup, memberRole);
    }

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
