package com.beetech.api_intern.features.carts.cartdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailRepository cartDetailRepository;

    @Override
    public Long getQuantityByCartId(Long cartId) {
        return cartDetailRepository.getQuantityByCart(cartId);
    }
}
