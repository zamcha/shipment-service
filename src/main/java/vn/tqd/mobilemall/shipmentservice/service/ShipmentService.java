package vn.tqd.mobilemall.shipmentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.tqd.mobilemall.shipmentservice.dto.request.CreateShipmentRequest;
import vn.tqd.mobilemall.shipmentservice.dto.request.UpdateStatusRequest;
import vn.tqd.mobilemall.shipmentservice.dto.response.ShipmentResponse;

import java.util.List;

public interface ShipmentService {
    // Tạo vận đơn (Gọi bởi RabbitMQ Listener)
    void createShipment(CreateShipmentRequest request);

    // Cập nhật trạng thái (Gọi bởi Admin qua API -> Bắn RabbitMQ về Mall)
    ShipmentResponse updateStatus(String shipmentId, UpdateStatusRequest request);

    // Xem vận đơn theo mã đơn hàng
    ShipmentResponse getShipmentByOrderId(String orderId, List<String> roles);
    void cancelShipment(String orderId);

    Page<ShipmentResponse> getAllShipments(String status, String keyword, Pageable pageable);
}