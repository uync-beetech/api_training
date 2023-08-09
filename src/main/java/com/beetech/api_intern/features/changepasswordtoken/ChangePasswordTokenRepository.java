package com.beetech.api_intern.features.changepasswordtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChangePasswordTokenRepository extends JpaRepository<ChangePasswordToken, Long> {
    Optional<ChangePasswordToken> findByTokenAndExpireDateIsAfter(String token, LocalDateTime currentTime);
}
