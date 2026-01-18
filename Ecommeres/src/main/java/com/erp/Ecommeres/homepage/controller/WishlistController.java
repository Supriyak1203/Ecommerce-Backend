package com.erp.Ecommeres.homepage.controller;

import com.erp.Ecommeres.homepage.dto.WishlistDTO;
import com.erp.Ecommeres.homepage.dto.WishlistResponseDTO;
import com.erp.Ecommeres.homepage.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin("*")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // ❤️ HEART ICON
    @PostMapping("/{productId}")
    public WishlistResponseDTO toggleWishlist(
            @RequestHeader("userId") Long userId,
            @PathVariable Long productId) {
        return wishlistService.toggleWishlist(userId, productId);
    }

    // 📃 WISHLIST PAGE
    @GetMapping
    public List<WishlistDTO> getWishlist(
            @RequestHeader("userId") Long userId) {
        return wishlistService.getWishlist(userId);
    }

    // ❌ REMOVE BUTTON
    @DeleteMapping("/{wishlistId}")
    public void remove(
            @RequestHeader("userId") Long userId,
            @PathVariable Long wishlistId) {
        wishlistService.removeWishlist(userId, wishlistId);
    }
}
