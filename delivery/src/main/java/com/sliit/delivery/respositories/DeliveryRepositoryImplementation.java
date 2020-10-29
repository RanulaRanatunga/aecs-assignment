package com.sliit.delivery.respositories;

import com.sliit.delivery.data.DBManager;
import com.sliit.delivery.entities.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeliveryRepositoryImplementation implements DeliveryRepository {


    DBManager dbManager;

    private String tblName = "Delivery_Details";
    String partitionKey = "TrackingId";

    @Override
    public String create(Delivery delivery) {

        try {
            delivery.setTrackingId(UUID.randomUUID().toString());
            dbManager.persistData(delivery, this.tblName, this.partitionKey, delivery.getTrackingId());
            return delivery.getTrackingId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    @Override
    public boolean cancel(String trackingId) {
        return dbManager.deleteDataItem(this.tblName, this.partitionKey, trackingId);
    }

    @Override
    public Delivery getByTrackingId(String trackingId) {
        try {
            return (Delivery) dbManager.getDataItem(this.tblName, this.partitionKey, trackingId, Delivery.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
