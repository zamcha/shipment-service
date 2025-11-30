package vn.tqd.mobilemall.shipmentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id", unique = true, nullable = false)
    private Integer orderId;

    @Column(name = "order_sn")
    private String orderSn;

    // Thông tin người nhận (Copy từ Order sang)
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;

    // Thông tin vận chuyển
    private String trackingNo; // Mã vận đơn (VD: GHTK-12345)

    // 0: Preparing, 1: Picked, 2: Delivering, 3: Delivered
    @Column(columnDefinition = "int default 0")
    private Integer status;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
