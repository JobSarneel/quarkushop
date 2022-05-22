package nl.spacedebris.monolith.repository;

import nl.spacedebris.monolith.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}