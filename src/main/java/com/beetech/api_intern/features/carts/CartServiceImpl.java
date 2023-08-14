package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.common.utils.StringGenerator;
import com.beetech.api_intern.common.utils.UserUtils;
import com.beetech.api_intern.features.carts.cartdetails.CartDetail;
import com.beetech.api_intern.features.carts.cartdetails.CartDetailRepository;
import com.beetech.api_intern.features.carts.dto.AddToCartDto;
import com.beetech.api_intern.features.carts.dto.DeleteCartDto;
import com.beetech.api_intern.features.carts.dto.UpdateCartDto;
import com.beetech.api_intern.features.carts.exceptions.CartNotFoundException;
import com.beetech.api_intern.features.products.Product;
import com.beetech.api_intern.features.products.ProductRepository;
import com.beetech.api_intern.features.products.exceptions.ProductNotFoundException;
import com.beetech.api_intern.features.user.User;
import com.beetech.api_intern.features.user.UserRepository;
import com.beetech.api_intern.features.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Cart service.
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Cart addToCart(AddToCartDto dto) {
        Optional<User> optionalUser = UserUtils.getAuthenticatedUser();
        Cart cart;
        // if user not authenticated
        if (optionalUser.isPresent()) {
            User user = userRepository.findById(optionalUser.get().getId()).orElseThrow(UserNotFoundException::getInstance);
            cart = user.getCart();
            if (cart == null) {
                cart = Cart.builder().user(user).userNote(dto.getUserNote()).build();
                cartRepository.save(cart);
            }
        } else if (dto.getToken() == null) {
            cart = Cart.builder().token(StringGenerator.getRandom20Chars()).userNote(dto.getUserNote()).build();
            cartRepository.save(cart);
        } else {
            cart = cartRepository.findByToken(dto.getToken()).orElseThrow(CartNotFoundException::getInstance);
        }

        cart.setUserNote(dto.getUserNote());

        Product product = productRepository.findById(dto.getProductId()).orElseThrow(ProductNotFoundException::getInstance);
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getId(), dto.getProductId());
        CartDetail cartDetail;
        if (optionalCartDetail.isPresent()) {
            cartDetail = optionalCartDetail.get();
            cartDetail.updateQuantity(cartDetail.getQuantity() + dto.getQuantity());
            cartDetailRepository.save(cartDetail);
        } else {
            cartDetail = CartDetail.builder().cart(cart).product(product).quantity(dto.getQuantity()).price(product.getPrice()).totalPrice(product.getPrice() * dto.getQuantity()).build();
            cartDetailRepository.save(cartDetail);
        }

        cart.addDetail(cartDetail);
        cartRepository.save(cart);

        return cart;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Cart syncCart(String token) {
        User user = userRepository.findById(UserUtils.getUser().getId()).orElseThrow(UserNotFoundException::getInstance);
        Cart userCart = user.getCart();
        Optional<Cart> optionalTokenCart = cartRepository.findByToken(token);
        if (optionalTokenCart.isEmpty()) {
            return null;
        }

        Cart tokenCart = optionalTokenCart.get();
        if (userCart == null) {
            tokenCart.setToken(null);
            tokenCart.setUser(user);
            cartRepository.save(tokenCart);
            return tokenCart;
        }

        tokenCart.getCartDetails().forEach(userCart::addDetail);

        // update user cart
        cartRepository.save(userCart);

        // delete temp cart
        cartRepository.delete(tokenCart);

        return userCart;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Cart deleteCart(DeleteCartDto dto) {
        Optional<User> optionalUser = UserUtils.getAuthenticatedUser();
        Cart cart = null;
        if (optionalUser.isPresent()) {
            User user = userRepository.findById(optionalUser.get().getId())
                    .orElseThrow(UserNotFoundException::getInstance);
            cart = user.getCart();
        } else if (dto.getToken() != null) {
            Optional<Cart> optionalCart = cartRepository.findByToken(dto.getToken());
            if (optionalCart.isPresent()) {
                cart = optionalCart.get();
            }
        }

        if (cart != null) {
            if (dto.getClearCart() == 1) {
                cartRepository.delete(cart);
                cart = null;
            } else if (dto.getClearCart() == 0) {
                Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByIdAndCartId(dto.getId(), cart.getId());
                if (optionalCartDetail.isPresent()) {
                    CartDetail cartDetail = optionalCartDetail.get();
                    cartRepository.updateTotalPriceById(cart.getTotalPrice() - cartDetail.getTotalPrice(), cart.getId());
                    cartDetailRepository.delete(cartDetail);
                }
            }
        }
        return cart;
    }

    @Override
    public Cart updateCart(UpdateCartDto dto) {
        Cart cart = null;
        Optional<User> optionalUser = UserUtils.getAuthenticatedUser();
        String token = dto.getToken();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            cart = user.getCart();
        } else if (token != null) {
            Optional<Cart> optionalCart = cartRepository.findByToken(token);
            if (optionalCart.isPresent()) {
                cart = optionalCart.get();
            }
        }

        if (cart != null) {
            Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByIdAndCartId(dto.getId(), cart.getId());
            if (optionalCartDetail.isPresent()) {
                CartDetail cartDetail = optionalCartDetail.get();
                // remove old cart detail
                cart.minusTotalPrice(cartDetail.getTotalPrice());

                // update cart detail
                cartDetail.setQuantity(dto.getQuantity());
                cartDetail.updateTotalPrice();
                cartDetailRepository.save(cartDetail);

                // update cart
                cart.plusTotalPrice(cartDetail.getTotalPrice());
                cartRepository.save(cart);
            }
        }
        return cart;
    }

}
