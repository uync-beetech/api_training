package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.dto.AddToCartDto;
import com.beetech.api_intern.features.carts.dto.DeleteCartDto;
import com.beetech.api_intern.features.carts.dto.UpdateCartDto;

public interface CartService {
    Cart addToCart(AddToCartDto dto);

    Cart syncCart(String token);

    Cart deleteCart(DeleteCartDto dto);

    Cart updateCart(UpdateCartDto dto);
}
