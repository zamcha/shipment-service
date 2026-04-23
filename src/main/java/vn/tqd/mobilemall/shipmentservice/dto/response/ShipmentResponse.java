package vn.tqd.mobilemall.shipmentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponse implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
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