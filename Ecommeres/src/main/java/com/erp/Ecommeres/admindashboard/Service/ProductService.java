package com.erp.Ecommeres.admindashboard.Service;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.admindashboard.dto.ProductDTO;
import com.erp.Ecommeres.admindashboard.entity.Product;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepo productRepository;

    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    // ================== ADD PRODUCT ==================
    public ProductDTO addProduct(ProductDTO dto) {
        Product product = dtoToEntity(dto);
        return entityToDto(productRepository.save(product));
    }

    // ================== GET ALL PRODUCTS ==================
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // ================== GET PRODUCT BY ID ==================
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return entityToDto(product);
    }

    // ================== GET BY CATEGORY ==================
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // ================== SEARCH PRODUCT ==================
    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    // ================== UPDATE PRODUCT ==================
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(dto.getProductName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setDescription(dto.getDescription());
        product.setUsage(dto.getUsage());
        product.setDetails(dto.getDetails());
        product.setQuantity(dto.getQuantity());

        return entityToDto(productRepository.save(product));
    }

    // ================== DELETE PRODUCT ==================
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // ================== DTO → ENTITY ==================
    private Product dtoToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setDescription(dto.getDescription());
        product.setUsage(dto.getUsage());
        product.setDetails(dto.getDetails());
        product.setQuantity(dto.getQuantity());
        return product;
    }

    // ================== ENTITY → DTO ==================
    private ProductDTO entityToDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setDescription(product.getDescription());
        dto.setUsage(product.getUsage());
        dto.setDetails(product.getDetails());
        dto.setQuantity(product.getQuantity());
        return dto;
    }
}
