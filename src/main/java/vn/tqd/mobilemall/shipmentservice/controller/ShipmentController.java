package vn.tqd.mobilemall.shipmentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.tqd.mobilemall.common.api.response.ApiResponse;
import vn.tqd.mobilemall.shipmentservice.dto.request.CreateShipmentRequest;
import vn.tqd.mobilemall.shipmentservice.dto.request.UpdateStatusRequest;
import vn.tqd.mobilemall.shipmentservice.dto.response.ShipmentResponse;
import vn.tqd.mobilemall.shipmentservice.service.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipment Controller", description = "Quản lý vận đơn")
public class ShipmentController {

    private final ShipmentService shipmentService;
    @Value("${internal-apikey.secret}")
    private String internalApiKey;
    @Operation(summary = "Tạo vận đơn thủ công", description = "Dành cho Admin/Manager xử lý sự cố hoặc tạo test")
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // Chỉ quản lý mới được tạo tay
    public ResponseEntity<ApiResponse<Void>> createShipment(@Valid @RequestBody CreateShipmentRequest request) {
        shipmentService.createShipment(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "Cập nhật trạng thái vận đơn", description = "Cập nhật tiến độ giao hàng (VD: Đang giao, Đã giao)")
    @PutMapping("/{shipmentId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // User không được tự update trạng thái
    public ResponseEntity<ApiResponse<String>> updateStatus(
            @PathVariable String shipmentId,
            @Valid @RequestBody UpdateStatusRequest request) {

        shipmentService.updateStatus(shipmentId, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật trạng thái thành công"));
    }
    @Operation(summary = " Lấy tất cả đơn vận chuyển")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<Page<ShipmentResponse>>> getAllShipments(
            @RequestParam(required = false) String status,   // Filter: SUCCESS, FAILED...
            @RequestParam(required = false) String keyword,  // Search: OrderId, Mã GD...
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        // Tạo đối tượng Pageable
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ShipmentResponse> result = shipmentService.getAllShipments(status, keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Operation(summary = "Xem chi tiết vận đơn theo Order ID", description = "User xem đơn mình, Admin xem mọi đơn")
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<ShipmentResponse>> getByOrderId(@PathVariable String orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        List<String> roles = jwt.getClaimAsStringList("roles");
        // Lưu ý: Để bảo mật tuyệt đối, trong Service nên check xem User hiện tại có sở hữu OrderId này không
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getShipmentByOrderId(orderId,roles)));
    }

    @Operation(summary = "Hủy vận đơn", description = "Thường dùng cho Admin khi đơn hàng bị hủy hoặc giao thất bại")
    @PostMapping("/order/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // User nên hủy bên OrderService, không hủy trực tiếp ở đây
    public ResponseEntity<ApiResponse<Void>> cancelShipment(@PathVariable String orderId) {
        shipmentService.cancelShipment(orderId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    // --- API MỚI (Dành cho OrderService gọi) ---
    // Endpoint này KHÔNG dùng @PreAuthorize role, mà check header bí mật
    @PostMapping("/internal/order/{orderId}/cancel")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<Void>> cancelShipmentInternal(
            @PathVariable String orderId,
            @RequestHeader("X-Internal-Api-Key") String apiKey) {

        // 1. Kiểm tra Secret Key để đảm bảo chỉ OrderService mới gọi được
        if (!internalApiKey.equals(apiKey)) {
            throw new RuntimeException("Truy cập bị từ chối: Sai Internal Key");
            // Hoặc trả về 403 Forbidden
        }

        // 2. Thực hiện logic hủy
        shipmentService.cancelShipment(orderId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}