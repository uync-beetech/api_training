package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.cartdetails.CartDetailService;
import com.beetech.api_intern.features.carts.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;
    private final CartDetailService cartDetailService;

    @PostMapping("add-cart")
    public ResponseEntity<AddToCartResponse> addToCart(@Valid @RequestBody AddToCartRequest dto) {
        Cart cart = cartService.addToCart(dto);
        Long totalQuantity = cartDetailService.getQuantityByCartId(cart.getId());
        AddToCartResponse data = AddToCartResponse.builder()
                .token(cart.getToken())
                .totalQuantity(totalQuantity)
                .build();

        return ResponseEntity.ok(data);
    }

    @PostMapping("sync-cart")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Long> syncCart(@Valid @RequestBody SyncCartRequest dto) {
        Cart synchronizedCart = cartService.syncCart(dto.getToken());
        if (synchronizedCart == null) {
            return ResponseEntity.ok().build();
        }
        Long totalQuantity = cartDetailService.getQuantityByCartId(synchronizedCart.getId());

        return ResponseEntity.ok(totalQuantity);
    }

    @PostMapping("delete-cart")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<DeleteCartResponse> deleteCart(@Valid @RequestBody DeleteCartRequest dto) {
        Cart deletedCart = cartService.deleteCart(dto);
        DeleteCartResponse data = new DeleteCartResponse(0L);
        if (deletedCart != null) {
            data = new DeleteCartResponse(cartDetailService.getQuantityByCartId(deletedCart.getId()));
        }

        return ResponseEntity.ok(data);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("update-cart")
    public ResponseEntity<UpdateCartResponse> updateCart(@Valid @RequestBody UpdateCartRequest dto) {
        UpdateCartResponse data = new UpdateCartResponse(0L);
        Cart updatedCart = cartService.updateCart(dto);
        if (updatedCart != null) {
            data.setTotalQuantity(cartDetailService.getQuantityByCartId(updatedCart.getId()));
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping("cart-info")
    public ResponseEntity<FindCartInfoResponse> findCartInfo(@Valid @RequestBody FindCartInfoRequest dto) {
        var data = cartService.findCartInfo(dto);

        return ResponseEntity.ok(data);
    }

    @PostMapping("cart-quantity")
    public ResponseEntity<FindTotalQuantityResponse> findTotalQuantity(@Valid @RequestBody FindTotalQuantityRequest dto) {
        var data = cartService.findTotalQuantity(dto.getToken());
        return ResponseEntity.ok(data);
    }
}
