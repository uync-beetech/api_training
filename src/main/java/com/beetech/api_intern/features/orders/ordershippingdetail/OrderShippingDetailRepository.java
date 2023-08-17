package com.beetech.api_intern.features.orders.ordershippingdetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderShippingDetailRepository extends JpaRepository<OrderShippingDetail, Long> {
}
