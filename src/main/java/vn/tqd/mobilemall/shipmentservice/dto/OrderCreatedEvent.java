package vn.tqd.mobilemall.shipmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer orderId;
    private String orderSn;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String note;
    private List<String> productNames;
}
