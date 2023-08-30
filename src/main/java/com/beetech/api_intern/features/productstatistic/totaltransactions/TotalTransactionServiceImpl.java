package com.beetech.api_intern.features.productstatistic.totaltransactions;

import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.features.products.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TotalTransactionServiceImpl implements TotalTransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TotalTransactionServiceImpl.class);
    private final TotalTransactionRepository totalTransactionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(Product product) {
        // get today date
        LocalDate today = LocalDate.now();
        // convert to string
        String stringToday = DateTimeFormatterUtils.convertLocalDateToString(today);
        // update total count
        int successfulTotalTransactionUpdate = totalTransactionRepository.update(product.getId(), stringToday);
        // if success, break
        if (successfulTotalTransactionUpdate != 0) {
            return;
        }

        // Create a loop for creating new or updating with 2 iterations.
        for (int i = 0; i < 2; i++) {
            // Try creating a new record for the total count.
            try {
                // find last previous record
                var optionalTotalTransaction = totalTransactionRepository.findLast(product.getId());

                Long totalCount = 0L;
                // If the previous record does exist
                if (optionalTotalTransaction.isPresent()) {
                    totalCount = optionalTotalTransaction.get().getTransactions();
                }
                totalCount++;
                totalTransactionRepository.create(product.getId(), stringToday, totalCount);
                LOGGER.info("create total transaction count");
                return;
            } catch (Exception e) {
                LOGGER.info("create total transaction count failed");
            }

            try {
                // If creating a new record is unsuccessful
                // update total
                successfulTotalTransactionUpdate = totalTransactionRepository.update(product.getId(), stringToday);
                // If the update is successful, stop the function.
                if (successfulTotalTransactionUpdate != 0) {
                    LOGGER.info("update total transaction count");
                    return;
                }
            } catch (Exception e) {
                LOGGER.info("update total transaction count failed");
            }
        }
    }
}
