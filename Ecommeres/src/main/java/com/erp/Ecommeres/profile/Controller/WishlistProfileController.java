package com.erp.Ecommeres.profile.Controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.profile.entity.WishlistProfile;
import com.erp.Ecommeres.profile.service.WishlistProfileService;



@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistProfileController {

    private final WishlistProfileService wishlistService;

    public WishlistProfileController(WishlistProfileService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // ADD TO WISHLIST
    @PostMapping(consumes = "application/json")
    public ResponseEntity<WishlistProfile> addWishlist(
            @RequestBody WishlistProfile wishlist) {

        WishlistProfile saved = wishlistService.addToWishlist(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET MY WISHLIST
    @GetMapping("/{userId}")
    public ResponseEntity<List<WishlistProfile>> getWishlist(
            @PathVariable Long userId) {

        return ResponseEntity.ok(wishlistService.getWishlist(userId));
    }

    // DELETE WISHLIST ITEM
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlistItem(id);
        return ResponseEntity.ok("Wishlist item removed");
    }
}
