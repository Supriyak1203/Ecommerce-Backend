package com.erp.Ecommeres.profile.Controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.profile.entity.MyOrder;
import com.erp.Ecommeres.profile.service.MyOrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class MyOrderController {

    private final MyOrderService myOrderService;

    public MyOrderController(MyOrderService myOrderService) {
        this.myOrderService = myOrderService;
    }

    @PostMapping
    public ResponseEntity<MyOrder> placeOrder(@RequestBody MyOrder order) {
        return ResponseEntity.ok(myOrderService.placeOrder(order));
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<MyOrder>> myOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(myOrderService.getMyOrders(userId));
    }
}
