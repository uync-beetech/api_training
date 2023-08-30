package com.beetech.api_intern.features.productstatistic.numaddtocart;

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
public class NumberAddToCartServiceImpl implements NumberAddToCartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberAddToCartServiceImpl.class);
    private final NumberAddToCartRepository numberAddToCartRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(Product product) {
        // get today date
        LocalDate today = LocalDate.now();
        // convert to string
        String stringToday = DateTimeFormatterUtils.convertLocalDateToString(today);
        // update added count
        int successfulAddedCountUpdate = numberAddToCartRepository.update(product.getId(), stringToday);
        // if success, break
        if (successfulAddedCountUpdate != 0) {
            return;
        }

        // Create a loop for creating new or updating with 2 iterations.
        for (int i = 0; i < 2; i++) {
            // Try creating a new record for the added count.
            try {
                // find last previous record
                var optionalAdded = numberAddToCartRepository.findLast(product.getId());

                Long addedCount = 0L;
                // If the previous record does exist
                if (optionalAdded.isPresent()) {
                    addedCount = optionalAdded.get().getAddToCartCount();
                }
                addedCount++;
                numberAddToCartRepository.create(product.getId(), stringToday, addedCount);
                LOGGER.info("create add to cart count");
                return;
            } catch (Exception e) {
                LOGGER.info("create add to cart count failed");
            }

            try {
                // If creating a new record is unsuccessful
                // update added count
                successfulAddedCountUpdate = numberAddToCartRepository.update(product.getId(), stringToday);
                // If the update is successful, stop the function.
                if (successfulAddedCountUpdate != 0) {
                    LOGGER.info("update add to cart count");
                    return;
                }
            } catch (Exception e) {
                LOGGER.info("update add to cart count failed");
            }
        }
    }
}
