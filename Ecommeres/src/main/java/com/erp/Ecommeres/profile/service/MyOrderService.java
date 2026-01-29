package com.erp.Ecommeres.profile.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.profile.entity.MyOrder;
import com.erp.Ecommeres.profile.repo.MyOrderRepository;


@Service
public class MyOrderService {

    private final MyOrderRepository myOrderRepository;

    public MyOrderService(MyOrderRepository myOrderRepository) {
        this.myOrderRepository = myOrderRepository;
    }

    public MyOrder placeOrder(MyOrder order) {

        order.setOrderNumber("ORD-" + System.currentTimeMillis());

        if (order.getOrderStatus() == null) {
            order.setOrderStatus("PLACED");
        }

        return myOrderRepository.save(order);
    }

    public List<MyOrder> getMyOrders(Long userId) {
        return myOrderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
