package com.beetech.api_intern.features.user;

import com.beetech.api_intern.security.RefreshToken;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login_id")
    @Getter
    private String loginId;

    @Column(name = "user_name", unique = true, length = 40)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_day")
    private LocalDate birthday;

    @Column(name = "locked")
    private boolean locked = false;

    @Column(name = "deleted")
    private boolean deleted = false;

    @OneToMany(mappedBy = "user")
    private Collection<RefreshToken> refreshTokens = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
