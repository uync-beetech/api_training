package com.beetech.api_intern.features.products.productstatistic.totalsales;

import com.beetech.api_intern.features.products.Product;

public interface TotalSaleService {
    void update(Product product, Long quantity);
}
