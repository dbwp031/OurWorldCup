    package com.example.ourworldcup.domain.relation;

    import com.example.ourworldcup.domain.AuditingFields;
    import com.example.ourworldcup.domain.Worldcup;
    import com.example.ourworldcup.domain.constant.MemberRole;
    import com.example.ourworldcup.domain.userAccount.UserAccount;
    import jakarta.persistence.*;
    import lombok.*;

    import java.util.Objects;

    @Builder
    @Getter
    @AllArgsConstructor(access= AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString(callSuper = true)
    @Entity
    public class Member extends AuditingFields {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

        @ToString.Exclude
        @ManyToOne(optional = false) @JoinColumn(name="user_account_id")
        private UserAccount userAccount;

        @ToString.Exclude
        @ManyToOne(optional = false) @JoinColumn(name="worldcup_id")
        private Worldcup worldcup;

        @ToString.Exclude
        @Column(nullable = false) @Enumerated(EnumType.STRING)
        MemberRole memberRole;
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
