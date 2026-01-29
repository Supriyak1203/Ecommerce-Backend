package com.erp.Ecommeres.profile.repo;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.Ecommeres.profile.entity.MyOrder;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

    List<MyOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
}
