package vn.tqd.mobilemall.shipmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentRequest implements Serializable {
    private String orderId;
    private BigDecimal codAmount;          // Tiền thu hộ
    private Map<String, Object> receiverInfo; // Địa chỉ nhận hàng
    private String note;
}