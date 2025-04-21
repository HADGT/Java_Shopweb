package spring.api.management.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.api.management.models.Locations;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Locations, UUID> {
}
