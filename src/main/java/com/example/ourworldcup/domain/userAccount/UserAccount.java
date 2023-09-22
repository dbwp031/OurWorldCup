package com.example.ourworldcup.domain.userAccount;

import com.example.ourworldcup.domain.auth.AuthProvider;
import com.example.ourworldcup.domain.AuditingFields;
import com.example.ourworldcup.domain.auth.Authority;
import com.example.ourworldcup.domain.auth.Role;
import com.example.ourworldcup.domain.enums.AuthProviderType;
import com.example.ourworldcup.domain.enums.RoleType;
import com.example.ourworldcup.domain.relation.UserAccountRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class UserAccount extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String userId;
    @Column(nullable = false, length = 100) private String userName;
    @Column(length = 100) private String nickName;
    @Column(length = 100) private String email;
    private String picture;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private List<UserAccountRole> userAccountRoles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private AuthProvider authProvider;

    private String refreshToken;

    public UserAccount update(String userName) {
        this.userName = userName;
        return this;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(email, that.email) && Objects.equals(authProvider, that.authProvider);
    }

    public boolean equals(String email, AuthProviderType authProviderType) {
        if (this.email.equals(email) && this.authProvider.getAuthProviderType().equals(authProviderType)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, authProvider);
    }
}
