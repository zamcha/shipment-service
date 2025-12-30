package vn.tqd.mobilemall.shipmentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carriers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrier_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
}