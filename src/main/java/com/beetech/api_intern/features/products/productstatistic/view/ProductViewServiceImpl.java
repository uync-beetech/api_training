package com.beetech.api_intern.features.products.productstatistic.view;

import com.beetech.api_intern.features.products.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductViewServiceImpl implements ProductViewService {
    private final ProductViewRepository productViewRepository;

    @Async
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateView(Product product) {
        // find last view record of product
        Optional<ProductView> optionalProductView = productViewRepository.findTopByProductIdOrderByIdDesc(product.getId());
        ProductView newProductView = ProductView.builder()
                .product(product)
                .build();

        // if it has last record
        if (optionalProductView.isPresent()) {
            // get last view record
            ProductView lastProductView = optionalProductView.get();
            // get last view count
            newProductView.setCount(lastProductView.getCount());
        }
        // plus view
        newProductView.plusView();
        // save to database
        productViewRepository.save(newProductView);

    }
}
