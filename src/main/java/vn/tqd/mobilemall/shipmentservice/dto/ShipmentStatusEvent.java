package vn.tqd.mobilemall.shipmentservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ShipmentStatusEvent implements Serializable {
    private String orderId;
    private String newStatus;
}
