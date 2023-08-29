package com.beetech.api_intern.features.products.productstatistic.dto;

public interface ProductStatisticsInterface {
    Long getId();
    String getSku();
    String getName();
    Long getAddedFavorite();
    Long getRemovedFavorite();
    Long getView();
    Long getNumberAddToCarts();
    Long getTotalTransactions();
    Long getTotalSales();
    Double getTransactionViewRatio();
}
