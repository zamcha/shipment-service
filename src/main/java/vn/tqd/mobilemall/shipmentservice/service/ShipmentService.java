package vn.tqd.mobilemall.shipmentservice.service;

import vn.tqd.mobilemall.shipmentservice.dto.request.CreateShipmentRequest;
import vn.tqd.mobilemall.shipmentservice.dto.request.UpdateStatusRequest;
import vn.tqd.mobilemall.shipmentservice.dto.response.ShipmentResponse;

public interface ShipmentService {
    // Tạo vận đơn (Gọi bởi RabbitMQ Listener)
    void createShipment(CreateShipmentRequest request);

    // Cập nhật trạng thái (Gọi bởi Admin qua API -> Bắn RabbitMQ về Mall)
    ShipmentResponse updateStatus(String shipmentId, UpdateStatusRequest request);

    // Xem vận đơn theo mã đơn hàng
    ShipmentResponse getShipmentByOrderId(String orderId);
    void cancelShipment(String orderId);
}