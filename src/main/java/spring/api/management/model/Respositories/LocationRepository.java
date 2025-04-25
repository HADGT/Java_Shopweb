package spring.api.management.model.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.management.model.DTO.Locations;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Locations, UUID> {
    @Query("SELECT d FROM Locations d WHERE LOWER(d.StreetAddress) LIKE LOWER(CONCAT('%', :name , '%'))")
    List<Locations> findByNameContaining(@Param("name") String name);

    @Query("SELECT d FROM Locations d WHERE d.district.DistrictId = :id")
    List<Locations> findByDistrictId(@Param("id") UUID id);
}
