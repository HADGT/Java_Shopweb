package spring.api.management.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.api.management.models.Jobs;
import spring.api.management.models.Roles;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Roles, UUID> {
    @Query("SELECT d FROM Roles d WHERE LOWER(d.RoleName) like LOWER(CONCAT('%',:name,'%'))")
    List<Roles> findByNameContaining(@Param("name") String name);
}
