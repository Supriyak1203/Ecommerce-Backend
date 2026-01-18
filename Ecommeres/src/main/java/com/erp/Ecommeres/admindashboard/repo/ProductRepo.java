package com.erp.Ecommeres.admindashboard.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.Ecommeres.admindashboard.entity.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
