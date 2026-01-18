package com.erp.Ecommeres.homepage.controller;

import com.erp.Ecommeres.homepage.dto.CartDTO;
import com.erp.Ecommeres.homepage.dto.CartResponseDTO;
import com.erp.Ecommeres.homepage.service.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ✅ ADD TO CART
    @PostMapping("/add/{productId}")
    public CartResponseDTO addToCart(
            @RequestHeader("userId") Long userId,
            @PathVariable Long productId) {
        return cartService.addToCart(userId, productId);
    }

    // ✅ VIEW CART
    @GetMapping
    public CartResponseDTO getCart(
            @RequestHeader("userId") Long userId) {
        return cartService.getUserCart(userId);
    }

    // ✅ UPDATE QUANTITY
    @PutMapping("/update/{cartId}")
    public CartResponseDTO updateQty(
            @RequestHeader("userId") Long userId,
            @PathVariable Long cartId,
            @RequestParam int quantity) {
        return cartService.updateQuantity(cartId, quantity, userId);
    }

    // ✅ REMOVE ITEM
    @DeleteMapping("/remove/{cartId}")
    public CartResponseDTO removeItem(
            @RequestHeader("userId") Long userId,
            @PathVariable Long cartId) {
        return cartService.removeItem(cartId, userId);
    }
    
    @PostMapping("/move-to-wishlist/{cartId}")
    public ResponseEntity<String> moveToWishlist(
            @RequestHeader(value = "userId", required = false) Long userId,
            @PathVariable Long cartId) {

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing userId header");
        }

        cartService.moveToWishlist(userId, cartId);
        return ResponseEntity.ok("Item moved to wishlist successfully");
    }


}

