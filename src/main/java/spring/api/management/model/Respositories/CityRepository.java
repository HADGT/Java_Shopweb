package spring.api.management.model.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.management.model.DTO.Cities;

import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<Cities, UUID> {
    @Query("SELECT d FROM Cities d WHERE LOWER(d.CityName) LIKE LOWER(CONCAT('%', :name , '%'))")
    List<Cities> findByNameContaining(@Param("name") String name);

    @Query("SELECT d FROM Cities d WHERE LOWER(d.country.countryKey.CountryId) LIKE LOWER(CONCAT('%', :id , '%'))")
    List<Cities> findByCountryIDContaining(@Param("id") String id);
}
