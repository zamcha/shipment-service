package vn.tqd.mobilemall.shipmentservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentRequest implements Serializable {

    @NotBlank(message = "Mã đơn hàng không được để trống")
    private String orderId;

    @Min(value = 0, message = "Tiền thu hộ (COD) không được là số âm")
    private BigDecimal codAmount;

    @NotNull(message = "Thông tin người nhận không được để trống")
    private Map<String, Object> receiverInfo;
    // receiverInfo lưu dạng Map để linh động (bao gồm name, phone, address...)
    // Khi lưu xuống DB sẽ được convert thành JSON

    private String note; // Ghi chú cho shipper (ví dụ: "Giao giờ hành chính")
}