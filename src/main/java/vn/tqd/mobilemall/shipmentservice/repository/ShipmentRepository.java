package vn.tqd.mobilemall.shipmentservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tqd.mobilemall.shipmentservice.dto.response.ShipmentResponse;
import vn.tqd.mobilemall.shipmentservice.entity.Shipment;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    // Tìm vận đơn theo mã đơn hàng
    Optional<Shipment> findByOrderId(String orderId);
    Boolean existsByOrderId(String orderId);
    @Query("SELECT sp FROM Shipment sp WHERE " +
            "(:status IS NULL OR sp.status = :status) AND " +
            "(:keyword IS NULL OR sp.id LIKE %:keyword% OR sp.orderId LIKE %:keyword% )")
    Page<Shipment> findAllByFilter(
            @Param("status") String status,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}