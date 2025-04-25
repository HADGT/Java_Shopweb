package spring.api.management.model.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.management.model.DTO.Districts;

import java.util.List;
import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<Districts, UUID> {
    @Query("SELECT d FROM Districts d WHERE LOWER(d.DistrictName) LIKE LOWER(CONCAT('%', :name , '%'))")
    List<Districts> findByNameContaining(@Param("name") String name);

    @Query("SELECT d FROM Districts d WHERE d.city.CityId = :id")
    List<Districts> findByCityId(@Param("id") UUID id);
}
