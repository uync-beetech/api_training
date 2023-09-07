package com.beetech.api_intern.features.products.productstatistic.totalsales;

import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.features.products.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * The type Total sale service.
 */
@Service
@RequiredArgsConstructor
public class TotalSaleServiceImpl implements TotalSaleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TotalSaleServiceImpl.class);
    private final TotalSaleRepository totalSaleRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(Product product, Long quantity) {
        // get today date
        LocalDate today = LocalDate.now();
        // convert to string
        String stringToday = DateTimeFormatterUtils.convertLocalDateToString(today);
        // update total count
        int successfulTotalSaleUpdate = totalSaleRepository.update(product.getId(), stringToday, quantity);
        // if success, break
        if (successfulTotalSaleUpdate != 0) {
            return;
        }

        // Create a loop for creating new or updating with 2 iterations.
        for (int i = 0; i < 2; i++) {
            // Try creating a new record for the total count.
            try {
                // find last previous record
                var optionalTotalSale = totalSaleRepository.findLast(product.getId());

                Long totalCount = 0L;
                // If the previous record does exist
                if (optionalTotalSale.isPresent()) {
                    totalCount = optionalTotalSale.get().getSaleCount();
                }
                totalCount+=quantity;
                totalSaleRepository.create(product.getId(), stringToday, totalCount);
                LOGGER.info("create total sale count");
                return;
            } catch (Exception e) {
                LOGGER.info("create total sale count failed");
            }

            try {
                // If creating a new record is unsuccessful
                // update total
                successfulTotalSaleUpdate = totalSaleRepository.update(product.getId(), stringToday, quantity);
                // If the update is successful, stop the function.
                if (successfulTotalSaleUpdate != 0) {
                    LOGGER.info("update total sale count");
                    return;
                }
            } catch (Exception e) {
                LOGGER.info("update total sale count failed");
            }
        }
    }
}
