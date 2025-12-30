package vn.tqd.mobilemall.shipmentservice.service.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tqd.mobilemall.shipmentservice.config.RabbitMQConfig;
import vn.tqd.mobilemall.shipmentservice.dto.ShipmentStatusEvent;
import vn.tqd.mobilemall.shipmentservice.dto.request.CreateShipmentRequest;
import vn.tqd.mobilemall.shipmentservice.dto.request.UpdateStatusRequest;
import vn.tqd.mobilemall.shipmentservice.dto.response.ShipmentResponse;
import vn.tqd.mobilemall.shipmentservice.entity.Carrier;
import vn.tqd.mobilemall.shipmentservice.entity.Shipment;
import vn.tqd.mobilemall.shipmentservice.entity.enums.ShipmentStatus;
import vn.tqd.mobilemall.shipmentservice.exception.ResourceNotFoundException;
import vn.tqd.mobilemall.shipmentservice.mapper.ShipmentMapper;
import vn.tqd.mobilemall.shipmentservice.repository.CarrierRepository;
import vn.tqd.mobilemall.shipmentservice.repository.ShipmentRepository;
import vn.tqd.mobilemall.shipmentservice.service.ShipmentService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final CarrierRepository carrierRepository;
    private final RabbitTemplate rabbitTemplate; // Dùng để bắn tin về Mall
    private final ShipmentMapper shipmentMapper;
    @Value("${queue.mall-shipments.exchange}")
    private String exchange;
    @Value("${queue.mall-shipments.routing-key[1]}")
    private String routingKeyUpdate;

    @Override
    @Transactional
    public void createShipment(CreateShipmentRequest request) {
        log.info("Đang tạo vận đơn cho Order: {}", request.getOrderId());

        // 1. Kiểm tra tồn tại
        if (shipmentRepository.existsByOrderId(request.getOrderId())) {
            log.warn("Vận đơn cho đơn hàng {} đã tồn tại", request.getOrderId());
            return;
        }

        // 2. Chọn Carrier (Mặc định ID=1 hoặc logic chọn rẻ nhất)
        Carrier carrier = carrierRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn vị vận chuyển"));

        // 3. Tạo Entity
        Shipment shipment = Shipment.builder()
                .orderId(request.getOrderId())
                .carrier(carrier)
                .status(ShipmentStatus.READY_TO_PICK)
                .shippingFee(new BigDecimal("30000")) // Hardcode hoặc tính toán
                .codAmount(request.getCodAmount())
                .receiverInfo(request.getReceiverInfo())
                .estimatedDelivery(LocalDateTime.now().plusDays(3))
                .build();

        shipmentRepository.save(shipment);
        log.info("Tạo vận đơn thành công: {}", shipment.getId());
    }

    @Override
    @Transactional
    public ShipmentResponse updateStatus(String shipmentId, UpdateStatusRequest request) {
        // 1. Tìm vận đơn
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Vận đơn không tồn tại"));

        // 2. Cập nhật Status mới
        ShipmentStatus newStatus = ShipmentStatus.valueOf(request.getStatus());
        shipment.setStatus(newStatus);

        // Nếu giao thành công hoặc hoàn hàng thì set ngày kết thúc thực tế
        if (newStatus == ShipmentStatus.DELIVERED || newStatus == ShipmentStatus.RETURNED) {
            // shipment.setActualDelivery(LocalDateTime.now()); // Nếu có trường này
        }

        shipmentRepository.save(shipment);

        // 3. QUAN TRỌNG: Bắn RabbitMQ báo về Mall Service
        ShipmentStatusEvent event = ShipmentStatusEvent.builder()
                .orderId(shipment.getOrderId())
                .newStatus(newStatus.name())
                .build();

        // Gửi vào Exchange chung, routing key riêng cho update
        rabbitTemplate.convertAndSend(
               exchange,
                routingKeyUpdate,
                event
        );
        log.info("Đã cập nhật trạng thái {} và bắn event về Mall", newStatus);

        return shipmentMapper.toResponse(shipment);
    }

    @Override
    public ShipmentResponse getShipmentByOrderId(String orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Chưa có vận đơn cho đơn hàng này"));
        return shipmentMapper.toResponse(shipment);
    }

    @Override
    public void cancelShipment(String orderId) {
        // Tìm vận đơn theo Order ID
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Vận đơn không tồn tại cho order: " + orderId));

        // Chỉ cho hủy khi hàng chưa đi giao (Logic thực tế)
        if (shipment.getStatus() == ShipmentStatus.DELIVERING || shipment.getStatus() == ShipmentStatus.DELIVERED || shipment.getStatus() == ShipmentStatus.PICKED_UP) {
            throw new IllegalStateException("Không thể hủy vận đơn đã/đang giao!");
        }

        shipment.setStatus(ShipmentStatus.CANCELLED);
        shipmentRepository.save(shipment);

        // TODO: Nếu đã bắn sang GHN/GHTK thật thì phải gọi API bên đó để hủy lệnh lấy hàng
    }


}