package vn.tqd.mobilemall.shipmentservice.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ShipmentResponse {
    private String shipmentId;
    private String orderId;
    private String carrierName;
    private String status;
    private BigDecimal shippingFee;
    private BigDecimal codAmount;
    private Map<String, Object> receiverInfo;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}