package com.beetech.api_intern.features.dayoff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Integer> {
    boolean existsDayOffByDateEquals(LocalDate date);
}
