package com.erp.Ecommeres.profile.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.Ecommeres.profile.entity.WishlistProfile;


@Repository
public interface WishlistProfileRepository
        extends JpaRepository<WishlistProfile, Long> {

    List<WishlistProfile> findByUserIdOrderByCreatedAtDesc(Long userId);
}
