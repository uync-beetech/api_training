package com.beetech.api_intern.features.maintenancestatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MaintenanceStatusRepository extends JpaRepository<MaintenanceStatus, Long> {
    Optional<MaintenanceStatus> findFirstByTimeBeforeOrderByIdDesc(LocalDateTime time);
}
