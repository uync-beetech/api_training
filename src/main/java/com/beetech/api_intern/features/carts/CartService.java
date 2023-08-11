package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.dto.AddToCartDto;
import com.beetech.api_intern.features.user.User;

public interface CartService {
    Cart addToCart(User user, AddToCartDto dto);
}
