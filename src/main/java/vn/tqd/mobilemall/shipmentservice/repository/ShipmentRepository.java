package vn.tqd.mobilemall.shipmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tqd.mobilemall.shipmentservice.entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    // Kiểm tra xem đơn hàng này đã tạo vận đơn chưa (Chống trùng lặp RabbitMQ)
    boolean existsByOrderId(Integer orderId);

    Shipment findByOrderSn(String orderSn);
}
