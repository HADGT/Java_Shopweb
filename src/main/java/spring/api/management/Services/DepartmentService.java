package spring.api.management.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.models.Departments;
import spring.api.management.Respositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {
    private List<Departments> departmentList = new ArrayList<>();
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Departments> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Departments getDepartmentById(UUID id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public List<Departments> getDepartmentByName(String name) {
        return departmentRepository.findByNameContaining(name);
    }

    public Departments createDepartment(Departments departments) {
        return departmentRepository.save(departments);
    }

    public void deleteDepartment(UUID id) {
        departmentRepository.deleteById(id);
    }

    public Departments updateDepartment(UUID id, String Name) {
        // Kiểm tra xem department có tồn tại không
        Departments existingDepartment = getDepartmentById(id);
        if (existingDepartment == null) {
            logger.info("Không tìm thấy id phòng:" + id);
            return null;
        } else {
            existingDepartment.setDepartmentName(Name);
            return departmentRepository.save(existingDepartment);
        }
    }
}

