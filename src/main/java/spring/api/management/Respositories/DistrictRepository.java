package spring.api.management.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.api.management.models.Districts;

import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<Districts, UUID> {
}
