package nl.spacedebris.monolith.repository;

import nl.spacedebris.monolith.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCartCustomerId(Long customerId);

    Optional<Order> findByPaymentId(Long id);

}