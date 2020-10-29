package com.sliit.delivery.controllers;

import com.sliit.delivery.entities.Delivery;
import com.sliit.delivery.respositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")

public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @DeleteMapping("cancel/{trackingId}")
    public boolean cancel(@PathVariable("trackingId") final String trackingId) {
        return deliveryRepository.cancel(trackingId);
    }

    @PostMapping("create")
    public String create(@RequestBody Delivery delivery) {
        return deliveryRepository.create(delivery);
    }

    @GetMapping("getById/{trackingId}")
    public Delivery getByTrackingId(@PathVariable("trackingId") final String trackingId) {
        return deliveryRepository.getByTrackingId(trackingId);
    }
}
