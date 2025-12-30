-- ==========================================
-- MODULE SHIPMENT (Vận chuyển)
-- ==========================================

-- 1. Bảng carriers (Đơn vị vận chuyển)
CREATE TABLE IF NOT EXISTS carriers (
                                        carrier_id INT AUTO_INCREMENT NOT NULL,
                                        name VARCHAR(50) NOT NULL,
    PRIMARY KEY (carrier_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert dữ liệu mẫu
INSERT INTO carriers (name) VALUES ('Giao Hàng Nhanh'), ('Viettel Post'), ('Giao Hàng Tiết Kiệm');

-- 2. Bảng shipments (Vận đơn)
CREATE TABLE IF NOT EXISTS shipments (
                                         shipment_id CHAR(36) NOT NULL,          -- UUID
    order_id CHAR(36) NOT NULL,             -- Unique: 1 đơn chỉ 1 vận đơn active
    carrier_id INT NOT NULL,                -- FK
    status VARCHAR(20) DEFAULT 'READY_TO_PICK',
    shipping_fee DECIMAL(15, 2) NOT NULL,
    cod_amount DECIMAL(15, 2) DEFAULT 0,
    receiver_info JSON NOT NULL,            -- Thông tin người nhận
    estimated_delivery DATETIME,            -- Dự kiến giao
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (shipment_id),
    UNIQUE KEY uq_shipment_order (order_id),
    CONSTRAINT fk_shipments_carrier FOREIGN KEY (carrier_id) REFERENCES carriers (carrier_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;