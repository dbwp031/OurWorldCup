package com.example.ourworldcup.domain.relation;

import com.example.ourworldcup.domain.AuditingFields;
import com.example.ourworldcup.domain.auth.Role;
import com.example.ourworldcup.domain.userAccount.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class UserAccountRole extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserAccount userAccount;
}
