package com.beetech.api_intern.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserIdAndTokenAndBlockedIsFalse(Long userId, String token);

    @Modifying
    @Query("update RefreshToken r set r.blocked = true where r.user.id = :userId")
    void blockAllByUser(Long userId);
}
