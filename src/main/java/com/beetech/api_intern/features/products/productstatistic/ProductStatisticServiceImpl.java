package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.features.products.ProductRepository;
import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
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

    private LocalDate[] getStartDateAndEndDate(Integer year, Integer monthInt, Integer week, String date) {
        // Declare the statistical time range.
        LocalDate startDate;
        LocalDate endDate;

        // Check if a specific date is provided in the request
        if (date != null) {
            startDate = DateTimeFormatterUtils.convertStringToLocalDate(date);
            endDate = DateTimeFormatterUtils.convertStringToLocalDate(date);
        } else {

            // Check if a specific month is provided in the request
            if (year == null) {
                year = LocalDate.now().getYear();
            }

            if (monthInt == null) {
                // If no month is provided, set the start and end dates to cover the entire year
                startDate = LocalDate.of(year, Month.JANUARY, 1);
                endDate = LocalDate.of(year, Month.DECEMBER, 31);
            }

            // Check if a specific week is provided in the request
            else if (week == null) {
                Month month = Month.of(monthInt);
                // If no week is provided, set the start and end dates to cover the entire month
                LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
                startDate = firstDayOfMonth;
                endDate = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
            } else {
                Month month = Month.of(monthInt);
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

                // Calculate the start and end dates for the specified week in the month
                LocalDate firstDayOfWeek = firstDayOfMonth.with(weekFields.weekOfMonth(), week)
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
        LOGGER.info("Start date: {}", startDate);
        LOGGER.info("End date: {}", endDate);
        return new LocalDate[]{startDate, endDate};
    }

    @Override
    public Page<ProductStatisticsInterface> findAll(ProductStatisticsRequest request, Integer page, Integer size) {
        LocalDate[] dates = getStartDateAndEndDate(request.getYear(), request.getMonth(), request.getWeek(), request.getDate());
        String startDate = DateTimeFormatterUtils.convertLocalDateToString(dates[0]);
        String endDate = DateTimeFormatterUtils.convertLocalDateToString(dates[1]);

        // create pageable for pagination
        Pageable pageable = PageRequest.of(page, size);
        // query database
        return productRepository.findProductStatistics(startDate, endDate, pageable);
    }

    @Override
    public String generateCsv(ProductStatisticsRequest request, Integer page, Integer size) {
        Page<ProductStatisticsInterface> productStatistics = findAll(request, page, size);
        try (
                StringWriter writer = new StringWriter(); CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT)
        ) {
            // set csv header
            Object[] header = new String[]{
                    "id",
                    "name",
                    "sku",
                    "view",
                    "addedFavorite",
                    "removedFavorite",
                    "numberAddToCarts",
                    "totalTransactions",
                    "totalSales",
                    "transactionViewRatio"
            };
            csvPrinter.printRecord(header);
            for (ProductStatisticsInterface productStatistic : productStatistics.getContent()) {
                csvPrinter.printRecord(productStatistic.getId(),
                        productStatistic.getName(),
                        productStatistic.getSku(),
                        productStatistic.getView(),
                        productStatistic.getAddedFavorite(),
                        productStatistic.getRemovedFavorite(),
                        productStatistic.getNumberAddToCarts(),
                        productStatistic.getTotalTransactions(),
                        productStatistic.getTotalSales(),
                        productStatistic.getTransactionViewRatio());
            }
            csvPrinter.flush();
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
