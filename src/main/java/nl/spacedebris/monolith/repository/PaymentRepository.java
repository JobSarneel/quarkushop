package nl.spacedebris.monolith.repository;

import nl.spacedebris.monolith.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByAmountBetween(BigDecimal min, BigDecimal max);

}