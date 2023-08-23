package com.beetech.api_intern.features.maintenancestatus;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The type Maintenance status service.
 */
@Service
@RequiredArgsConstructor
public class MaintenanceStatusServiceImpl implements MaintenanceStatusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceStatusServiceImpl.class);
    private final MaintenanceStatusRepository maintenanceStatusRepo;

    @Override
    public Optional<MaintenanceStatus> findLastRecord() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return maintenanceStatusRepo.findFirstByTimeBeforeOrderByIdDesc(currentDateTime);
    }
}
