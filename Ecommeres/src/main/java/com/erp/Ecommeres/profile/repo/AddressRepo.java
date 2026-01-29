package com.erp.Ecommeres.profile.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.erp.Ecommeres.profile.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {

    // Latest address (recommended)
    Optional<Address> findTopByUserIdOrderByCreatedAtDesc(Long userId);

    // Simple fetch
    Optional<Address> findFirstByUserId(Long userId);

	List<Address> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    
}
