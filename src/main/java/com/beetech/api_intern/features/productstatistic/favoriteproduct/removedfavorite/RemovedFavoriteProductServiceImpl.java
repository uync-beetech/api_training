package com.beetech.api_intern.features.productstatistic.favoriteproduct.removedfavorite;

import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.features.products.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemovedFavoriteProductServiceImpl implements RemovedFavoriteProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemovedFavoriteProductServiceImpl.class);
    private final RemovedFavoriteProductRepository removedFavoriteProductRepository;

    @Override
    public void updateCount(Product product) {
        // get today date
        LocalDate today = LocalDate.now();
        // convert to string
        String stringToday = DateTimeFormatterUtils.convertLocalDateToString(today);
        // update removed count
        int successfulAddedCountUpdate = removedFavoriteProductRepository.update(product.getId(), stringToday);
        // if success, break
        if (successfulAddedCountUpdate != 0) {
            return;
        }

        // Create a loop for creating new or updating with 2 iterations.
        for (int i = 0; i < 2; i++) {
            // Try creating a new record for the removed favorite count.
            try {
                // find last previous record
                Optional<RemovedFavoriteProduct> optionalAddedFavoriteProduct = removedFavoriteProductRepository.findLast(product.getId());

                Long removedCount = 0L;
                // If the previous record does exist
                if (optionalAddedFavoriteProduct.isPresent()) {
                    removedCount = optionalAddedFavoriteProduct.get().getRemovedCount();
                }
                removedCount++;
                removedFavoriteProductRepository.create(product.getId(), stringToday, removedCount);
                LOGGER.info("create removed favorite product count");
                return;
            } catch (Exception e) {
                LOGGER.info("create removed favorite product count failed");
            }

            try {
                // If creating a new record is unsuccessful
                // update removed count
                successfulAddedCountUpdate = removedFavoriteProductRepository.update(product.getId(), stringToday);
                // If the update is successful, stop the function.
                if (successfulAddedCountUpdate != 0) {
                    LOGGER.info("update removed favorite product count");
                    return;
                }
            } catch (Exception e) {
                LOGGER.info("update removed favorite product count failed");
            }
        }
    }
}
