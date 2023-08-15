package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.dto.*;

public interface CartService {
    Cart addToCart(AddToCartDto dto);

    Cart syncCart(String token);

    Cart deleteCart(DeleteCartDto dto);

    Cart updateCart(UpdateCartDto dto);
    FindCartInfoResponse findCartInfo(FindCartInfoDto dto);
    FindTotalQuantityResponse findTotalQuantity(String token);
}
