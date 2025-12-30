package vn.tqd.mobilemall.shipmentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tqd.mobilemall.common.api.response.ApiResponse;
import vn.tqd.mobilemall.shipmentservice.dto.request.CreateShipmentRequest;
import vn.tqd.mobilemall.shipmentservice.dto.request.UpdateStatusRequest;
import vn.tqd.mobilemall.shipmentservice.dto.response.ShipmentResponse;
import vn.tqd.mobilemall.shipmentservice.service.ShipmentService;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipment Controller", description = "Quản lý vận đơn")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    // API này thường dùng để test hoặc Admin tạo thủ công nếu RabbitMQ lỗi
    @Operation(summary = "Tạo vận đơn thủ công (Cho Admin/Test)")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createShipment(@Valid @RequestBody CreateShipmentRequest request) {
        shipmentService.createShipment(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @Operation(summary = "Cập nhật trạng thái vận đơn (Shipper/Admin)")
    @PutMapping("/{shipmentId}/status")
    public ResponseEntity<ApiResponse<ShipmentResponse>> updateStatus(
            @PathVariable String shipmentId,
            @Valid @RequestBody UpdateStatusRequest request) {

        ShipmentResponse response = shipmentService.updateStatus(shipmentId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','MANAGER')")
    @Operation(summary = "Xem chi tiết vận đơn theo Order ID")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<ShipmentResponse>> getByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getShipmentByOrderId(orderId)));
    }
    @PostMapping("/order/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelShipment(@PathVariable String orderId) {
        shipmentService.cancelShipment(orderId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}