package com.beetech.api_intern.features.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByLoginId(String loginId);
    boolean existsByUsername(String username);
    boolean existsByLoginId(String loginId);
    List<User> findAllByBirthDayGreaterThanEqualAndBirthDayLessThanEqual(String from, String to);
}
