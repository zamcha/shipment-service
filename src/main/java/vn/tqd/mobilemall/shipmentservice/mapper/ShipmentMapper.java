package vn.tqd.mobilemall.shipmentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.tqd.mobilemall.shipmentservice.dto.response.ShipmentResponse;
import vn.tqd.mobilemall.shipmentservice.entity.Shipment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    // 1. Map ID của bảng Shipment sang shipmentId của Response
    @Mapping(source = "id", target = "shipmentId")

    // 2. Map tên Carrier (Lấy từ Object con Carrier ra)
    @Mapping(source = "carrier.name", target = "carrierName")

    // 3. Map Enum Status sang String (MapStruct tự động làm, nhưng khai báo cho rõ)
    @Mapping(source = "status", target = "status")

    ShipmentResponse toResponse(Shipment shipment);

    // Map danh sách (Nếu sau này dùng)
    List<ShipmentResponse> toResponseList(List<Shipment> shipments);
}