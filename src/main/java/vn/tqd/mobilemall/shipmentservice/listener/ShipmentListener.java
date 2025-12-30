package vn.tqd.mobilemall.shipmentservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vn.tqd.mobilemall.shipmentservice.dto.ShipmentRequest;
import vn.tqd.mobilemall.shipmentservice.service.ShipmentService;
import vn.tqd.mobilemall.shipmentservice.dto.request.CreateShipmentRequest; // DTO của Service

@Component
@Slf4j
@RequiredArgsConstructor
public class ShipmentListener {

    private final ShipmentService shipmentService;

    @RabbitListener(queues = "${queue.mall-shipments.queue[0]}")
    public void receiveShipmentRequest(ShipmentRequest request) {
        log.info("Nhận yêu cầu tạo vận đơn từ RabbitMQ: OrderID = {}", request.getOrderId());

        try {
            // Map từ DTO RabbitMQ sang DTO của Service (nếu khác nhau)
            CreateShipmentRequest serviceRequest = new CreateShipmentRequest();
            serviceRequest.setOrderId(request.getOrderId());
            serviceRequest.setCodAmount(request.getCodAmount());
            serviceRequest.setReceiverInfo(request.getReceiverInfo());
            serviceRequest.setNote(request.getNote());

            // Gọi logic tạo vận đơn (Lưu DB)
            shipmentService.createShipment(serviceRequest);

            log.info("Đã tạo vận đơn thành công!");

        } catch (Exception e) {
            log.error("Lỗi tạo vận đơn: ", e);
            // RabbitMQ sẽ tự retry hoặc đẩy vào Dead Letter Queue
        }
    }
}