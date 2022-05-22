package nl.spacedebris.monolith.repository;

import nl.spacedebris.monolith.model.Cart;
import nl.spacedebris.monolith.model.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByStatus(CartStatus status);

    List<Cart> findByStatusAndCustomerId(CartStatus status, Long customerId);

}
