package spring.api.management.model.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.api.management.model.DTO.Countries;

import java.util.List;
import java.util.Optional;

public interface CountryRespository extends JpaRepository<Countries, Integer> {
    @Query("SELECT d FROM Countries d WHERE LOWER(d.CountryName) LIKE LOWER(CONCAT('%', :name , '%'))")
    List<Countries> findByNameContaining(@Param("name") String name);
    // Tìm theo CountryId (trong countryKey)
    @Query("SELECT d FROM Countries d WHERE LOWER(d.countryKey.CountryId) LIKE LOWER(CONCAT('%', :id , '%'))")
    List<Countries> findByCountryIdContaining(@Param("id") String id);

    // Tìm theo ID (int)
    @Query("SELECT d FROM Countries d WHERE d.countryKey.ID = :id")
    Optional<Countries> findByIdInt(@Param("id") int id);
}
