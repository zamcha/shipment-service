package vn.tqd.mobilemall.shipmentservice.entity.enums;

public enum ShipmentStatus {
    READY_TO_PICK,  // Chờ lấy hàng
    PICKED_UP,      // Đã lấy hàng (Shipper đã lấy)
    DELIVERING,     // Đang giao
    DELIVERED,      // Giao thành công
    RETURNED,       // Hoàn hàng (Khách không nhận)
    CANCELLED       // Hủy vận đơn
}