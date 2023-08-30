package com.beetech.api_intern.features.products.productstatistic.favoriteproduct;

/**
 * The interface Favorite product service.
 */
public interface FavoriteProductService {
    /**
     * Add favorite product.
     *
     * @param sku the sku
     */
    void addFavoriteProduct(String sku);

    /**
     * Remove favorite product.
     *
     * @param sku the sku
     */
    void removeFavoriteProduct(String sku);
}
