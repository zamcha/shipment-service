package vn.tqd.mobilemall.shipmentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequest implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    @NotNull(message = "Trạng thái không được để trống")
    private String status; // PICKED_UP, DELIVERING, DELIVERED, CANCELLED, RETURNED

    private String reason; // Lý do (nếu Hủy/Hoàn)
}