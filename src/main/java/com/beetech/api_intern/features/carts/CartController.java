package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.common.exceptions.BadRequestException;
import com.beetech.api_intern.common.responses.CommonResponseBody;
import com.beetech.api_intern.features.carts.cartdetails.CartDetailService;
import com.beetech.api_intern.features.carts.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Cart controller.
 */
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartDetailService cartDetailService;

    /**
     * Add to cart response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("add-cart")
    public ResponseEntity<CommonResponseBody<AddToCartResponse>> addToCart(@Valid @RequestBody AddToCartRequest request) {
        Cart cart = cartService.addToCart(request);
        Long totalQuantity = cartDetailService.getQuantityByCartId(cart.getId());
        AddToCartResponse data = AddToCartResponse.builder()
                .token(cart.getToken())
                .totalQuantity(totalQuantity)
                .versionNo(cart.getVersionNo())
                .build();
        CommonResponseBody<AddToCartResponse> body = new CommonResponseBody<>(data);
        return ResponseEntity.ok(body);
    }

    /**
     * Sync cart response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("sync-cart")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<CommonResponseBody<Object>> syncCart(@Valid @RequestBody SyncCartRequest request) {
        Cart synchronizedCart = cartService.syncCart(request.getToken());
        if (synchronizedCart == null) {
            return ResponseEntity.ok(new CommonResponseBody<>());
        }
        Long totalQuantity = cartDetailService.getQuantityByCartId(synchronizedCart.getId());
        SyncCartResponse data = new SyncCartResponse(totalQuantity);
        CommonResponseBody<Object> body = new CommonResponseBody<>(data);

        return ResponseEntity.ok(body);
    }

    /**
     * Delete cart response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("delete-cart")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<CommonResponseBody<Object>> deleteCart(@Valid @RequestBody DeleteCartRequest request) {
        if (request.getClearCart() == 0 && request.getDetailId() == null) {
            throw new BadRequestException("detailId is required when clearCart with a value of 0");
        }
        Cart deletedCart = cartService.deleteCart(request);
        DeleteCartResponse data = new DeleteCartResponse(0L);
        if (deletedCart != null) {
            data = new DeleteCartResponse(cartDetailService.getQuantityByCartId(deletedCart.getId()));
        }

        return ResponseEntity.ok(new CommonResponseBody<>(data));
    }


    /**
     * Update cart response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("update-cart")
    public ResponseEntity<CommonResponseBody<UpdateCartResponse>> updateCart(@Valid @RequestBody UpdateCartRequest request) {
        UpdateCartResponse data = new UpdateCartResponse(0L);
        Cart updatedCart = cartService.updateCart(request);
        if (updatedCart != null) {
            data.setTotalQuantity(cartDetailService.getQuantityByCartId(updatedCart.getId()));
        }
        return ResponseEntity.ok(new CommonResponseBody<>(data));
    }

    /**
     * Find cart info response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("cart-info")
    public ResponseEntity<CommonResponseBody<Object>> findCartInfo(@Valid @RequestBody FindCartInfoRequest request) {
        var data = cartService.findCartInfo(request);

        return ResponseEntity.ok(new CommonResponseBody<>(data));
    }

    /**
     * Find total quantity response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("cart-quantity")
    public ResponseEntity<CommonResponseBody<FindTotalQuantityResponse>> findTotalQuantity(@Valid @RequestBody FindTotalQuantityRequest request) {
        var data = cartService.findTotalQuantity(request.getToken());
        return ResponseEntity.ok(new CommonResponseBody<>(data));
    }
}
