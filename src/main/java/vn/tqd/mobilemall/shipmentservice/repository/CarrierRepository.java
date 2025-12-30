package vn.tqd.mobilemall.shipmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tqd.mobilemall.shipmentservice.entity.Carrier;


@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Integer> {
}
