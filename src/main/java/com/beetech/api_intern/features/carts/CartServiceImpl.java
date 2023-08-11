package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.common.utils.StringGenerator;
import com.beetech.api_intern.features.carts.cartdetails.CartDetail;
import com.beetech.api_intern.features.carts.cartdetails.CartDetailRepository;
import com.beetech.api_intern.features.carts.dto.AddToCartDto;
import com.beetech.api_intern.features.carts.exceptions.CartNotFoundException;
import com.beetech.api_intern.features.products.Product;
import com.beetech.api_intern.features.products.ProductRepository;
import com.beetech.api_intern.features.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart addToCart(User user, AddToCartDto dto) {
        Cart cart = null;
        if (user != null) {
            cart = user.getCart();
            if (cart == null) {
                cart = Cart.builder()
                        .user(user)
                        .userNote(dto.getUserNote())
                        .build();
            }
        } else if (dto.getToken() == null) {
            cart = Cart.builder()
                    .token(StringGenerator.getRandom20Chars())
                    .userNote(dto.getUserNote())
                    .build();
        } else {
            cart = cartRepository.findByToken(dto.getToken()).orElseThrow(CartNotFoundException::getInstance);
        }
        Product product = productRepository.findById(dto.getProductId())
        CartDetail cartDetail = CartDetail.builder()
                .
        return cart;
    }
}
