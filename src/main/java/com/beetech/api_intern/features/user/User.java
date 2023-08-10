package com.beetech.api_intern.features.user;

import com.beetech.api_intern.features.changepasswordtoken.ChangePasswordToken;
import com.beetech.api_intern.features.role.Role;
import com.beetech.api_intern.security.RefreshToken;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login_id", unique = true)
    @Setter
    private String loginId;

    @Column(name = "old_login_id")
    @Builder.Default
    @Setter
    private String oldLoginId = null;


    @Column(name = "user_name", unique = true, length = 40)
    private String username;

    @Column(name = "password")
    @Setter
    private String password;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Getter
    @Column(name = "locked")
    @Builder.Default
    private boolean locked = false;

    @Column(name = "deleted_flag")
    @Builder.Default
    @Setter
    private int deleted = 0;


    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Collection<RefreshToken> refreshTokens = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Setter
    @Builder.Default
    private List<ChangePasswordToken> changePasswordTokens = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Setter
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public @NonNull String getPassword() {
        return this.password;
    }

    @Override
    public @NonNull String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
