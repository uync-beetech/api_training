package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.dto.*;

public interface CartService {
    Cart addToCart(AddToCartRequest dto);

    Cart syncCart(String token);

    Cart deleteCart(DeleteCartRequest dto);

    Cart updateCart(UpdateCartRequest dto);
    FindCartInfoResponse findCartInfo(FindCartInfoRequest dto);
    FindTotalQuantityResponse findTotalQuantity(String token);
}
