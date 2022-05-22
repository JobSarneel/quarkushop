package nl.spacedebris.monolith.repository;

import nl.spacedebris.monolith.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT p.reviews FROM Product p WHERE p.id = ?1")
    List<Review> findReviewsByProductId(Long id);

}