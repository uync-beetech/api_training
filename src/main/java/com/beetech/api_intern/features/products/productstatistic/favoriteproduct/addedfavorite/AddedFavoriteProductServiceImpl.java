package com.beetech.api_intern.features.products.productstatistic.favoriteproduct.addedfavorite;

import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.features.products.Product;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddedFavoriteProductServiceImpl implements AddedFavoriteProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddedFavoriteProductServiceImpl.class);

    private final AddedFavoriteProductRepository addedFavoriteProductRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCount(Product product) {
        // get today date
        LocalDate today = LocalDate.now();
        // convert to string
        String stringToday = DateTimeFormatterUtils.convertLocalDateToString(today);
        // update added count
        int successfulAddedCountUpdate = addedFavoriteProductRepository.update(product.getId(), stringToday);
        // if success, break
        if (successfulAddedCountUpdate != 0) {
            return;
        }

        // Create a loop for creating new or updating with 2 iterations.
        for (int i = 0; i < 2; i++) {
            // Try creating a new record for the added favorite count.
            try {
                // find last previous record
                Optional<AddedFavoriteProduct> optionalAddedFavoriteProduct = addedFavoriteProductRepository.findLast(product.getId());

                Long addedCount = 0L;
                // If the previous record does exist
                if (optionalAddedFavoriteProduct.isPresent()) {
                    addedCount = optionalAddedFavoriteProduct.get().getAddedCount();
                }
                addedCount++;
                addedFavoriteProductRepository.create(product.getId(), stringToday, addedCount);
                LOGGER.info("create added favorite product count");
                return;
            } catch (Exception e) {
                LOGGER.info("create added favorite product count failed");
            }

            try {
                // If creating a new record is unsuccessful
                // update added count
                successfulAddedCountUpdate = addedFavoriteProductRepository.update(product.getId(), stringToday);
                // If the update is successful, stop the function.
                if (successfulAddedCountUpdate != 0) {
                    LOGGER.info("update added favorite product count");
                    return;
                }
            } catch (Exception e) {
                LOGGER.info("update added favorite product count failed");
            }
        }
    }
}
