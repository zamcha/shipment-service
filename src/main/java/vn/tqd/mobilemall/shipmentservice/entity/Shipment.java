package vn.tqd.mobilemall.shipmentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.tqd.mobilemall.shipmentservice.entity.enums.ShipmentStatus;
import vn.tqd.mobilemall.shipmentservice.utils.BaseEntity;
import vn.tqd.mobilemall.shipmentservice.utils.JsonMapConverter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Shipment extends BaseEntity {
    // Kế thừa BaseEntity để có ID (UUID) và created_at/updated_at
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shipment_id")
    private String id;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @ManyToOne(fetch = FetchType.EAGER) // Eager để khi lấy shipment thì lấy luôn tên Carrier
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private ShipmentStatus status = ShipmentStatus.READY_TO_PICK;

    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    @Column(name = "cod_amount")
    private BigDecimal codAmount;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "receiver_info", columnDefinition = "json")
    private Map<String, Object> receiverInfo;

    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;
}