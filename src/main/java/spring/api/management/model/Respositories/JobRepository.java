package spring.api.management.model.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.management.model.DTO.Jobs;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Jobs, UUID> {
    @Query("SELECT d FROM Jobs d WHERE LOWER(d.JobTitle) like LOWER(CONCAT('%',:title,'%'))")
    List<Jobs> findByNameContaining(@Param("title") String title);

    @Query("SELECT d FROM Jobs d WHERE d.MinSalary >= :x AND d.MaxSalary <= :y")
    List<Jobs> JOBS_LIST(@Param("x")BigDecimal x, @Param("y") BigDecimal y);
}
