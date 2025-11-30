package vn.tqd.mobilemall.shipmentservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import vn.tqd.mobilemall.shipmentservice.dto.OrderCreatedEvent;
import vn.tqd.mobilemall.shipmentservice.entity.Shipment;
import vn.tqd.mobilemall.shipmentservice.repository.ShipmentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentListener {

    private final ShipmentRepository shipmentRepository;

    @RabbitListener(queues = "${queue.mall-shipments.queue}")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Nhận đơn hàng từ Mall Service: " + event.getOrderSn());

        // 1. Idempotency Check: Kiểm tra xem đã xử lý đơn này chưa?
        if (shipmentRepository.existsByOrderId(event.getOrderId())) {
            log.warn("Đơn hàng {} đã tồn tại vận đơn. Bỏ qua.", event.getOrderId());
            return;
        }

        // 2. Tạo Shipment mới
        Shipment shipment = Shipment.builder()
                .orderId(event.getOrderId())
                .orderSn(event.getOrderSn())
                .receiverName(event.getReceiverName())
                .receiverPhone(event.getReceiverPhone())
                .receiverAddress(event.getReceiverAddress())
                .status(0) // 0: Preparin
                .trackingNo("SHIP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();

        // 3. Lưu vào DB
        shipmentRepository.save(shipment);

        log.info("Đã tạo vận đơn thành công: " + shipment.getTrackingNo());
    }
}
