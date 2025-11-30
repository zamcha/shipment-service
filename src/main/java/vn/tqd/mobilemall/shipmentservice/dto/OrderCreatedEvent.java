package vn.tqd.mobilemall.shipmentservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderCreatedEvent {
    private Integer orderId;
    private String orderSn;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String note;
    private List<String> productNames;
}
