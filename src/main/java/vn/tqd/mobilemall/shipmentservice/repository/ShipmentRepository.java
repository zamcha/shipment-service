package vn.tqd.mobilemall.shipmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tqd.mobilemall.shipmentservice.entity.Shipment;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    // Tìm vận đơn theo mã đơn hàng
    Optional<Shipment> findByOrderId(String orderId);
    Boolean existsByOrderId(String orderId);
}