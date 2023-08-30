package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.features.products.ProductRepository;
import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductStatisticServiceImpl implements ProductStatisticService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductStatisticServiceImpl.class);
    private final ProductRepository productRepository;

    @Override
    public Page<ProductStatisticsInterface> findAll(ProductStatisticsRequest request, Integer page, Integer size) {
        // Declare the statistical time range.
        LocalDate startDate;
        LocalDate endDate;

        // Check if a specific date is provided in the request
        if (request.getDate() != null) {
            startDate = DateTimeFormatterUtils.convertStringToLocalDate(request.getDate());
            endDate = DateTimeFormatterUtils.convertStringToLocalDate(request.getDate());
        } else {

            // Check if a specific month is provided in the request
            if (request.getYear() != null && request.getMonth() == null) {
                // If no month is provided, set the start and end dates to cover the entire year
                startDate = LocalDate.of(request.getYear(), Month.JANUARY, 1);
                endDate = LocalDate.of(request.getYear(), Month.DECEMBER, 31);
            } else {
                int year = LocalDate.now().getYear();

                // Check if a specific week is provided in the request
                if (request.getMonth() != null && request.getWeek() == null) {
                    Month month = Month.of(request.getMonth());
                    // If no week is provided, set the start and end dates to cover the entire month
                    LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
                    startDate = firstDayOfMonth;
                    endDate = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
                } else {
                    Month month = LocalDate.now().getMonth();
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

                    // Calculate the start and end dates for the specified week in the month
                    LocalDate firstDayOfWeek = firstDayOfMonth.with(weekFields.weekOfMonth(), request.getWeek())
                            .with(TemporalAdjusters.previousOrSame(weekFields.getFirstDayOfWeek()));
                    LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);
                    LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

                    // Adjust the end date if the calculated end of week date is beyond the end of the month
                    if (lastDayOfWeek.isAfter(lastDayOfMonth)) {
                        lastDayOfWeek = lastDayOfMonth;
                    }
                    startDate = firstDayOfWeek;
                    endDate = lastDayOfWeek;
                }
            }
        }
        LOGGER.info("Start date: {}", startDate);
        LOGGER.info("End date: {}", endDate);

        // create pageable for pagination
        Pageable pageable = PageRequest.of(page, size);
        // query database
        return productRepository.findProductStatistics(DateTimeFormatterUtils.convertLocalDateToString(startDate), DateTimeFormatterUtils.convertLocalDateToString(endDate), pageable);
    }
}
