package Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.URLMapping;

public interface UrlMappingRepository extends JpaRepository<URLMapping, Long > {
	Optional<URLMapping> findByShortCode(String shortCode);
}
