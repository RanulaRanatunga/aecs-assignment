package com.sliit.order.respository;

import com.sliit.order.entities.Order;
import java.util.List;

public interface OrderRepository {

    String addOrder(Order order);

    Order getOrderById(String orderId);

    List<Order> getAll();
}
