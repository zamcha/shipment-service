package vn.tqd.mobilemall.shipmentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tqd.mobilemall.shipmentservice.dto.OrderCreatedEvent;
import vn.tqd.mobilemall.shipmentservice.entity.Shipment;
import vn.tqd.mobilemall.shipmentservice.listener.ShipmentListener;
import vn.tqd.mobilemall.shipmentservice.repository.ShipmentRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentListener sendNotificationService;

    @GetMapping
    public List<Shipment> getAll() {
        return shipmentRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody OrderCreatedEvent request) {
        sendNotificationService.handleOrderCreated(request);
        return ResponseEntity.noContent().build();
    }
}
