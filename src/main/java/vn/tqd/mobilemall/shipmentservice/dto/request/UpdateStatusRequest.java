package vn.tqd.mobilemall.shipmentservice.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class UpdateStatusRequest {
    @NotNull(message = "Trạng thái không được để trống")
    private String status; // PICKED_UP, DELIVERING, DELIVERED, CANCELLED, RETURNED

    private String reason; // Lý do (nếu Hủy/Hoàn)
}