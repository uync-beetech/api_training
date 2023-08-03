package com.beetech.api_intern.features.maintenancestatus;

import java.util.Optional;

public interface MaintenanceStatusService {
    Optional<MaintenanceStatus> findLastRecord();
}
