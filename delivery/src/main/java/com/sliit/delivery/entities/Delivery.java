package com.sliit.delivery.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Delivery {

    private String trackingId;
    private String status;

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
