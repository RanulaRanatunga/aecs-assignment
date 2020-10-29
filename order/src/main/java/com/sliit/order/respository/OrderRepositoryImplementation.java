package com.sliit.order.respository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sliit.order.data.DBManager;
import com.sliit.order.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryImplementation implements OrderRepository{

    DBManager dbManager;

    private String tblName = "Order_Details";
    String partitionKey = "OrderId";

    @Override
    public String addOrder(Order order) {
        try {
            order.setOrderId(UUID.randomUUID().toString());
            order.calculateTotal();
            dbManager.persistData(order, this.tblName, this.partitionKey, order.getOrderId());
            return order.getOrderId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    @Override
    public Order getOrderById(String orderId) {
        try {
            return (Order) dbManager.getDataItem(this.tblName, this.partitionKey, orderId, Order.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Order> getAll() {
        try {
            List<Object> data = dbManager.getAll(this.tblName, Order.class);
            return data.stream().filter(obj -> obj instanceof Order).map(o -> (Order) o).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
