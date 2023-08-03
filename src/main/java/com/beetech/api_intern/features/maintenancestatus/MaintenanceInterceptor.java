package com.beetech.api_intern.features.maintenancestatus;

import com.beetech.api_intern.config.WorkingDateTimeConfig;
import com.beetech.api_intern.features.dayoff.DayOffService;
import com.sun.tools.javac.Main;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
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

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        String errorMessage = "{\"error\": \"Server is maintaining\"}";
        response.getWriter().write(errorMessage);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
//            Case 1: last record is maintaining
            var optionalLastMaintenanceStatus = maintenanceStatusService.findLastRecord();
            if (optionalLastMaintenanceStatus.isPresent()) {
                MaintenanceStatus lastMaintenanceStatus = optionalLastMaintenanceStatus.get();
                if (lastMaintenanceStatus.isFlag()) {
                    LOGGER.info("maintaining case 1");
                    setResponse(response);
                    return false;
                }
            }

            // Case 2: today is day off
            boolean isTodayDayOff = dayOffService.isTodayDayOff();
            if (isTodayDayOff) {
                LOGGER.info("maintaining case 2");
                setResponse(response);
                return false;
            }

            // Case 3: today is not working day of week
            List<DayOfWeek> workingDaysOfWeek = workingDateTimeConfig.getDaysOfWeek();
            LocalDate currentDate = LocalDate.now();
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (!workingDaysOfWeek.contains(dayOfWeek)) {
                LOGGER.info("maintaining case 3");
                setResponse(response);
                return false;
            }

            // Case 4: now is not in working time
            LocalTime currentTime = LocalTime.now();
            LocalTime startTime = workingDateTimeConfig.getStartTime();
            LocalTime endTime = workingDateTimeConfig.getEndTime();

            if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
                LOGGER.info("maintaining case 4");
                setResponse(response);
                return false;
            }
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }
        return true;
    }
}
