package com.beetech.api_intern.features.productstatistic.view;

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
public class ProductViewServiceImpl implements ProductViewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductViewServiceImpl.class);
    private final ProductViewRepository productViewRepository;

    @Override
    public void updateView(Product product) {
        // get today date
        LocalDate today = LocalDate.now();
        // convert to string
        String stringToday = DateTimeFormatterUtils.convertLocalDateToString(today);
        // update view
        int successfulViewUpdate = productViewRepository.updateViewByProductIdAndDate(product.getId(), stringToday);
        // if success, break
        if (successfulViewUpdate != 0) {
            return;
        }

        // Create a loop for creating new or updating with 2 iterations.
        for (int i = 0; i < 2; i++) {
            // Try creating a new record for the product's view count.
            try {
                // find last previous record
                Optional<ProductView> optionalLastProductView = productViewRepository.findTopByProductIdOrderByDateDesc(product.getId());
                // create new record for this date
                // default count = 0
                ProductViewKey productViewKey = new ProductViewKey(product.getId(), stringToday);
                ProductView productView = ProductView.builder().id(productViewKey).build();
                // If the previous record does not exist
                if (optionalLastProductView.isEmpty()) {
                    // Increment the view count by one
                    productView.plusView();
                } else {
                    productView.setViewCount(optionalLastProductView.get().getViewCount() + 1);
                }
                productViewRepository.createProductView(product.getId(), stringToday, productView.getViewCount());
                LOGGER.info("create product view success");
                return;
            } catch (Exception e) {
                LOGGER.error("create failed");

            }

            try {
                // If creating a new record is unsuccessful
                // update view
                successfulViewUpdate = productViewRepository.updateViewByProductIdAndDate(product.getId(), stringToday);
                // If the update is successful, stop the function.
                if (successfulViewUpdate != 0) {
                    return;
                }
            } catch (Exception e) {
                LOGGER.error("update failed");
            }
        }
    }
}