package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.common.utils.StringGenerator;
import com.beetech.api_intern.common.utils.UserUtils;
import com.beetech.api_intern.features.carts.cartdetails.CartDetail;
import com.beetech.api_intern.features.carts.cartdetails.CartDetailRepository;
import com.beetech.api_intern.features.carts.cartdetails.CartDetailResponse;
import com.beetech.api_intern.features.carts.dto.*;
import com.beetech.api_intern.features.carts.exceptions.CartNotFoundException;
import com.beetech.api_intern.features.numaddtocart.NumberAddToCartRepository;
import com.beetech.api_intern.features.products.Product;
import com.beetech.api_intern.features.products.ProductRepository;
import com.beetech.api_intern.features.products.exceptions.ProductNotFoundException;
import com.beetech.api_intern.features.productstatistic.ProductStatistic;
import com.beetech.api_intern.features.productstatistic.ProductStatisticRepository;
import com.beetech.api_intern.features.user.User;
import com.beetech.api_intern.features.user.UserRepository;
import com.beetech.api_intern.features.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final NumberAddToCartRepository numberAddToCartRepository;
    private final ProductStatisticRepository productStatisticRepository;

    private Optional<Cart> findCartByUserOrToken(String token) {
        Optional<User> optionalUser = UserUtils.getAuthenticatedUser();
        Cart cart = null;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            cart = user.getCart();
        } else if (token != null) {
            cart = cartRepository.findByToken(token).orElseThrow(CartNotFoundException::getInstance);
        }
        if (cart == null) {
            return Optional.empty();
        }
        return Optional.of(cart);
    }

    @Override
    public Cart addToCart(AddToCartRequest dto) {
        Optional<User> optionalUser = UserUtils.getAuthenticatedUser();
        Cart cart;
        // if user not authenticated
        if (optionalUser.isPresent()) {
            User user = userRepository.findById(optionalUser.get().getId()).orElseThrow(UserNotFoundException::getInstance);
            cart = user.getCart();
            // check if user not has a cart yet
            if (cart == null) {
                // create new cart
                cart = Cart.builder().user(user).build();
                cartRepository.save(cart);
            }
        } else if (dto.getToken() == null) {
            cart = Cart.builder().token(StringGenerator.getRandom20Chars()).build();
            cartRepository.save(cart);
        } else {
            cart = cartRepository.findByToken(dto.getToken()).orElseThrow(CartNotFoundException::getInstance);
        }

        // find product by id
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(ProductNotFoundException::getInstance);
        // find cart detail contain this product
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getId(), dto.getProductId());
        CartDetail cartDetail;
        // If there is already a cartDetail containing this product
        if (optionalCartDetail.isPresent()) {
            cartDetail = optionalCartDetail.get();
            // update quantity
            cartDetail.updateQuantity(cartDetail.getQuantity() + dto.getQuantity());
            cart.plusOne();
            cartDetailRepository.save(cartDetail);
        } else {
            // If there is no cartDetail containing this product, create new cartDetail
            cartDetail = CartDetail.builder().cart(cart).product(product).quantity(dto.getQuantity()).price(product.getPrice()).totalPrice(product.getPrice() * dto.getQuantity()).build();
            cartDetailRepository.save(cartDetail);
        }

        // find productStatistic based by product. If it doesn't exist, create a new record.
        ProductStatistic productStatistic = productStatisticRepository.findByProductId(product.getId())
                .orElse(ProductStatistic.builder().product(product).build());
        // update number add to cart
        productStatistic.plusNumberAddToCart();
        // save to database
        productStatisticRepository.save(productStatistic);


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
            tokenCart.plusOne();
            cartRepository.save(tokenCart);
            return tokenCart;
        }

        var tokenCartDetails = tokenCart.getCartDetails();

        // foreach in token cart detail
        tokenCartDetails.forEach(tokenCartDetail -> {
            Long productId = tokenCartDetail.getProduct().getId();
            // check if exist user cart detail contain this product
            Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByCartIdAndProductId(userCart.getId(), productId);
            if (optionalCartDetail.isPresent()) {
                CartDetail userCartDetail = optionalCartDetail.get();
                userCartDetail.updateQuantity(userCartDetail.getQuantity() + tokenCartDetail.getQuantity());
                cartDetailRepository.save(userCartDetail);
            } else {
                tokenCartDetail.setCart(userCart);
                CartDetail cartDetail = CartDetail.builder()
                        .cart(userCart)
                        .product(tokenCartDetail.getProduct())
                        .quantity(tokenCartDetail.getQuantity())
                        .build();
                cartDetailRepository.save(cartDetail);
                userCart.addDetail(cartDetail);
                cartRepository.save(userCart);
            }
        });

        // update version no
        cartRepository.updateVersionNo(userCart.getId());

        // delete temp cart
        cartRepository.delete(tokenCart);

        return userCart;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Cart deleteCart(DeleteCartRequest dto) {
        Cart cart = findCartByUserOrToken(dto.getToken()).orElseThrow(CartNotFoundException::getInstance);

        // if clearCart = 1, delete cart
        if (dto.getClearCart() == 1) {
            cartRepository.delete(cart);
            cart = null;
        } else
            // if clearCart = 0, delete cartDetail
            if (dto.getClearCart() == 0) {
                // find cartDetail by cartDetailId and cartId
                Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByIdAndCartId(dto.getId(), cart.getId());
                // if cart detail is existed
                if (optionalCartDetail.isPresent()) {
                    CartDetail cartDetail = optionalCartDetail.get();
                    cartRepository.updateTotalPriceById(cart.getTotalPrice() - cartDetail.getTotalPrice(), cart.getId());

                    // update version no
                    cartRepository.updateVersionNo(cart.getId());

                    // delete cart detail
                    cartDetailRepository.delete(cartDetail);
                }
            }
        return cart;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Cart updateCart(UpdateCartRequest dto) {
        Cart cart = findCartByUserOrToken(dto.getToken()).orElseThrow(CartNotFoundException::getInstance);
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
        return cart;
    }

    @Override
    public FindCartInfoResponse findCartInfo(FindCartInfoRequest dto) {
        Cart cart = findCartByUserOrToken(dto.getToken()).orElseThrow(CartNotFoundException::getInstance);

        List<CartDetailResponse> details = cart.getCartDetails().stream().map(CartDetailResponse::new).toList();

        return FindCartInfoResponse.builder()
                .id(cart.getId())
                .totalPrice(cart.getTotalPrice())
                .versionNo(cart.getVersionNo())
                .details(details)
                .build();
    }

    @Override
    public FindTotalQuantityResponse findTotalQuantity(String token) {
        Cart cart = findCartByUserOrToken(token)
                .orElseThrow(CartNotFoundException::getInstance);
        Long totalQuantity = cartDetailRepository.getQuantityByCart(cart.getId());
        return new FindTotalQuantityResponse(totalQuantity, cart.getVersionNo());
    }

}
