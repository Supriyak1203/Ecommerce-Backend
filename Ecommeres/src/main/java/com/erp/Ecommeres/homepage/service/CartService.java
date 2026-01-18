package com.erp.Ecommeres.homepage.service;

import com.erp.Ecommeres.admindashboard.entity.Product;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;
import com.erp.Ecommeres.homepage.dto.CartDTO;
import com.erp.Ecommeres.homepage.dto.CartResponseDTO;
import com.erp.Ecommeres.homepage.entity.Cart;
import com.erp.Ecommeres.homepage.entity.Wishlist;
import com.erp.Ecommeres.homepage.repo.CartRepo;
import com.erp.Ecommeres.homepage.repo.WishlistRepo;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final WishlistRepo wishlistRepo;

    private static final double GST_RATE = 0.05; // 5% GST

    public CartService(CartRepo cartRepo,
                       ProductRepo productRepo,
                       WishlistRepo wishlistRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.wishlistRepo = wishlistRepo;
    }

    // ================= ADD TO CART =================
    public CartResponseDTO addToCart(Long userId, Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Cart cart = cartRepo.findByUserIdAndProductId(userId, productId)
                .orElse(new Cart());

        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setPrice(product.getPrice());

        int qty = cart.getQuantity() == null ? 1 : cart.getQuantity() + 1;
        cart.setQuantity(qty);
        cart.setTotalPrice(qty * product.getPrice());

        cartRepo.save(cart);
        return buildCartResponse(userId);
    }

    // ================= VIEW CART =================
    public CartResponseDTO getUserCart(Long userId) {
        return buildCartResponse(userId);
    }

    // ================= UPDATE QUANTITY =================
    public CartResponseDTO updateQuantity(Long cartId, int quantity, Long userId) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        if (!Objects.equals(cart.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (quantity <= 0) {
            cartRepo.deleteById(cartId);
            return buildCartResponse(userId);
        }

        cart.setQuantity(quantity);
        cart.setTotalPrice(quantity * cart.getPrice());
        cartRepo.save(cart);

        return buildCartResponse(userId);
    }

    // ================= REMOVE ITEM =================
    public CartResponseDTO removeItem(Long cartId, Long userId) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        if (!Objects.equals(cart.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        cartRepo.deleteById(cartId);
        return buildCartResponse(userId);
    }

    // ================= MOVE TO WISHLIST =================
    @Transactional
    public void moveToWishlist(Long userId, Long cartId) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        if (!Objects.equals(cart.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Long productId = cart.getProductId();

        if (!wishlistRepo.existsByUserIdAndProductId(userId, productId)) {

            Product product = productRepo.findById(productId)
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            Wishlist wishlist = new Wishlist();
            wishlist.setUserId(userId);
            wishlist.setProductId(productId);
            wishlist.setImageUrl(product.getImageUrl());

            wishlistRepo.save(wishlist);
        }

        
    }

    // ================= HELPER =================
    private CartResponseDTO buildCartResponse(Long userId) {

        List<Cart> carts = cartRepo.findByUserId(userId);

        List<CartDTO> items = carts.stream().map(cart -> {
            Product product = productRepo.findById(cart.getProductId())
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            CartDTO dto = new CartDTO();
            dto.setId(cart.getId());
            dto.setProductId(cart.getProductId());
            dto.setProductName(product.getProductName());
            dto.setImageUrl(product.getImageUrl());
            dto.setQuantity(cart.getQuantity());
            dto.setPrice(cart.getPrice());
            dto.setTotalPrice(cart.getTotalPrice());
            return dto;
        }).collect(Collectors.toList());

        double subTotal = items.stream()
                .mapToDouble(CartDTO::getTotalPrice)
                .sum();

        double gst = subTotal * GST_RATE;
        double grandTotal = subTotal + gst;

        CartResponseDTO response = new CartResponseDTO();
        response.setItems(items);
        response.setSubTotal(subTotal);
        response.setGst(gst);
        response.setGrandTotal(grandTotal);

        return response;
    }
}
