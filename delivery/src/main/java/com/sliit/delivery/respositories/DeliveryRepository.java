package com.sliit.delivery.respositories;

import com.sliit.delivery.entities.Delivery;

public interface DeliveryRepository {

    String create(Delivery delivery);

    boolean cancel(String trackingId);

    Delivery getByTrackingId(String trackingId);
}
