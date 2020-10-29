package com.sliit.order.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    private String customerId;
    private String customerName;
    private Address customerAddress;
}
