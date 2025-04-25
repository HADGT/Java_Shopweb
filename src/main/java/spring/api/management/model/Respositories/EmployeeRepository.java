package spring.api.management.model.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.management.model.DTO.Employees;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, UUID> {
    @Query("SELECT d FROM Employees d " +
            "WHERE LOWER(d.FirstName) LIKE LOWER(CONCAT('%', :name , '%')) " +
            " OR LOWER(d.LastName) LIKE LOWER(CONCAT('%', :name , '%'))")
    List<Employees> searchByName(@Param("name") String name);

    @Query("SELECT d FROM Employees d WHERE d.CreatedAt BETWEEN :start AND :end")
    List<Employees> findByCreatedAtBetween(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    @Query("SELECT d FROM Employees d WHERE d.UpdatedAt BETWEEN :start AND :end")
    List<Employees> findByUpdatedAtBetween(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    @Query("SELECT d FROM Employees d WHERE d.JobId = :id")
    List<Employees> findByJobId(@Param("id") UUID id);

    @Query("SELECT e FROM Employees e " +
            "WHERE (:ID IS NULL OR e.location.district.city.country.countryKey.ID = :ID) " +
            "AND (:CityId IS NULL OR e.location.district.city.CityId = :CityId) " +
            "AND (:DistrictId IS NULL OR e.location.district.DistrictId = :DistrictId) " +
            "AND (:LocationId IS NULL OR e.location.LocationId = :LocationId)")
    List<Employees> findByAddress(
            @Param("ID") Integer ID,
            @Param("CityId") UUID CityId,
            @Param("DistrictId") UUID DistrictId,
            @Param("LocationId") UUID LocationId
    );

    @Query("SELECT d FROM Employees d WHERE d.Status = :status")
    List<Employees> findByStatus(@Param("status") boolean status);

}
