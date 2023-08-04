package com.beetech.api_intern.features.maintenancestatus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaintenanceStatusServiceImplTest {
    @Mock
    MaintenanceStatusRepository maintenanceStatusRepository;

    @InjectMocks
    MaintenanceStatusServiceImpl maintenanceStatusService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test find last record, when has record")
    void findLastRecord_WhenHasRecord() {
        when(maintenanceStatusRepository.findFirstByTimeBeforeOrderByIdDesc(any(LocalDateTime.class)))
                .thenAnswer(invocationOnMock -> Optional.of(MaintenanceStatus
                        .builder()
                        .id(1L)
                        .time(LocalDateTime.now().minusHours(1L))
                        .flag(false)
                        .build()));
        Optional<MaintenanceStatus> optionalMaintenanceStatus = maintenanceStatusService.findLastRecord();
        assertTrue(optionalMaintenanceStatus.isPresent());
        assertTrue(optionalMaintenanceStatus.get().getTime().isBefore(LocalDateTime.now()));
    }

    @Test
    @DisplayName("Test find last record, when has no record")
    void findLastRecord_WhenHasNoRecord() {
        when(maintenanceStatusRepository.findFirstByTimeBeforeOrderByIdDesc(any(LocalDateTime.class)))
                .thenAnswer(invocationOnMock -> Optional.empty());
        assertTrue(maintenanceStatusService.findLastRecord().isEmpty());
    }
}