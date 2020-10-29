package com.sliit.order.controllers;

import com.sliit.order.entities.Order;
import com.sliit.order.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("create")
    public String createOrder(@RequestBody Order order) {
        return orderRepository.addOrder(order);
    }

    @GetMapping("getById/{orderId}")
    public Order getOrderByOrderId(@PathVariable("orderId") final String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    @GetMapping("getAll")
    public List<Order> getAll() {
        return orderRepository.getAll();
    }
}
