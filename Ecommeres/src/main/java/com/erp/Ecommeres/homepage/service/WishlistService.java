package com.erp.Ecommeres.homepage.service;

import com.erp.Ecommeres.admindashboard.entity.Product;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;
import com.erp.Ecommeres.homepage.dto.WishlistDTO;
import com.erp.Ecommeres.homepage.dto.WishlistResponseDTO;
import com.erp.Ecommeres.homepage.entity.Wishlist;
import com.erp.Ecommeres.homepage.repo.WishlistRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepo wishlistRepo;
    private final ProductRepo productRepo;

    public WishlistService(WishlistRepo wishlistRepo, ProductRepo productRepo) {
        this.wishlistRepo = wishlistRepo;
        this.productRepo = productRepo;
    }

    // ❤️ ADD / REMOVE (HEART ICON)
    public WishlistResponseDTO toggleWishlist(Long userId, Long productId) {

        WishlistResponseDTO response = new WishlistResponseDTO();

        return wishlistRepo.findByUserIdAndProductId(userId, productId)
                .map(existing -> {
                    // REMOVE
                    wishlistRepo.delete(existing);
                    response.setStatus("REMOVED");
                    response.setItem(null);
                    return response;
                })
                .orElseGet(() -> {
                    // ADD
                    Product product = productRepo.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    Wishlist wishlist = new Wishlist();
                    wishlist.setUserId(userId);
                    wishlist.setProductId(productId);
                    wishlist.setImageUrl(product.getImageUrl());

                    wishlist = wishlistRepo.save(wishlist);

                    WishlistDTO dto = new WishlistDTO();
                    dto.setId(wishlist.getId());
                    dto.setProductId(productId);
                    dto.setProductName(product.getProductName());
                    dto.setImageUrl(product.getImageUrl());

                    response.setStatus("ADDED");
                    response.setItem(dto);
                    return response;
                });
    }

    // 📃 GET USER WISHLIST (WISHLIST PAGE)
    public List<WishlistDTO> getWishlist(Long userId) {

        return wishlistRepo.findByUserId(userId)
                .stream()
                .map(wishlist -> {
                    Product product = productRepo.findById(wishlist.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    WishlistDTO dto = new WishlistDTO();
                    dto.setId(wishlist.getId());
                    dto.setProductId(wishlist.getProductId());
                    dto.setProductName(product.getProductName());
                    dto.setImageUrl(wishlist.getImageUrl());
                    return dto;
                })
                .toList();
    }

    // ❌ REMOVE FROM WISHLIST PAGE
    public void removeWishlist(Long userId, Long wishlistId) {

        Wishlist wishlist = wishlistRepo.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        if (!wishlist.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized delete");
        }

        wishlistRepo.delete(wishlist);
    }
}
