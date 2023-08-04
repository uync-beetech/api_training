package com.beetech.api_intern.features.maintenancestatus;

import com.beetech.api_intern.config.WorkingDateTimeConfig;
import com.beetech.api_intern.features.dayoff.DayOffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MaintenanceInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceInterceptor.class);
    private final MaintenanceStatusService maintenanceStatusService;
    private final DayOffService dayOffService;
    private final WorkingDateTimeConfig workingDateTimeConfig;

    private boolean isMaintaining() {
        try {
            //            Case 1: last record is maintaining
            var optionalLastMaintenanceStatus = maintenanceStatusService.findLastRecord();
            if (optionalLastMaintenanceStatus.isPresent()) {
                MaintenanceStatus lastMaintenanceStatus = optionalLastMaintenanceStatus.get();
                if (lastMaintenanceStatus.isFlag()) {
                    LOGGER.info("maintaining case 1");
                    return true;
                }
            }

            // Case 2: today is day off
            boolean isTodayDayOff = dayOffService.isTodayDayOff();
            if (isTodayDayOff) {
                LOGGER.info("maintaining case 2");
                return true;
            }

            // Case 3: today is not working day of week
            List<DayOfWeek> workingDaysOfWeek = workingDateTimeConfig.getDaysOfWeek();
            LocalDate currentDate = LocalDate.now();
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (!workingDaysOfWeek.contains(dayOfWeek)) {
                LOGGER.info("maintaining case 3");
                return true;
            }

            // Case 4: now is not in working time
            LocalTime currentTime = LocalTime.now();
            LocalTime startTime = workingDateTimeConfig.getStartTime();
            LocalTime endTime = workingDateTimeConfig.getEndTime();

            // start time is before end time, check current time in (startTime, endTime)
            if (startTime.isBefore(endTime) && (currentTime.isBefore(startTime) || currentTime.isAfter(endTime))) {
                LOGGER.info("maintaining case 4");
                return true;
            }

            // start time is after end time, check current time in (endTime, startTime)
            if (startTime.isAfter(endTime) && currentTime.isAfter(endTime) && currentTime.isBefore(startTime)) {
                LOGGER.info("maintaining case 4");
                return true;
            }

        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isMaintaining()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            String errorMessage = "{\"error\": \"Server is maintaining\"}";
            response.getWriter().write(errorMessage);
            return false;
        }
        return true;
    }
}
