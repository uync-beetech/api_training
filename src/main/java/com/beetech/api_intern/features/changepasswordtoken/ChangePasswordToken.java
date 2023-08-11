package com.beetech.api_intern.features.changepasswordtoken;

import com.beetech.api_intern.features.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "change_password_token")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "expire_date")
    @Getter
    @Builder.Default
    private LocalDateTime expireDate = LocalDateTime.now().plusMinutes(30);

    @Column(name = "token")
    @Getter
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
