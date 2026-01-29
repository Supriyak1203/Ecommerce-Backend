package com.erp.Ecommeres.profile.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.profile.entity.WishlistProfile;
import com.erp.Ecommeres.profile.repo.WishlistProfileRepository;


@Service
public class WishlistProfileService {

    private final WishlistProfileRepository wishlistRepository;

    public WishlistProfileService(WishlistProfileRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    // ADD TO WISHLIST
    public WishlistProfile addToWishlist(WishlistProfile wishlist) {
        return wishlistRepository.save(wishlist);
    }

    // GET USER WISHLIST
    public List<WishlistProfile> getWishlist(Long userId) {
        return wishlistRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // DELETE WISHLIST ITEM
    public void deleteWishlistItem(Long id) {
        wishlistRepository.deleteById(id);
    }
}
