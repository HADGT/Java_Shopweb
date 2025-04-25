package spring.api.management.model.Respositories;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.api.management.model.DTO.Departments;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, UUID> {
    // Tìm kiếm danh sách phòng ban có tên chứa từ khóa
    @Query("SELECT d FROM Departments d WHERE LOWER(d.DepartmentName) LIKE LOWER(CONCAT('%', :name , '%'))")
    List<Departments> findByNameContaining(@Param("name") String name);
}
