package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.dto.*;

public interface CartService {
    Cart addToCart(AddToCartRequest request);

    Cart syncCart(String token);

    Cart deleteCart(DeleteCartRequest request);

    Cart updateCart(UpdateCartRequest request);
    FindCartInfoResponse findCartInfo(FindCartInfoRequest request);
    FindTotalQuantityResponse findTotalQuantity(String token);
}
