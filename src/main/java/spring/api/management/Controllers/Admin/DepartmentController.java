package spring.api.management.Controllers.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.Resource.Department.Request;
import spring.api.management.Resource.Department.Response;
import spring.api.management.helper.ActionMessage;
import spring.api.management.models.Departments;
import spring.api.management.Services.DepartmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        try {
            List<Departments> departments = departmentService.getAllDepartments();
            if (departments == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!");
            } else {
                return ResponseEntity.ok(departments);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getDepartmentByName(@RequestParam String name) {
        try {
            List<Departments> departments = departmentService.getDepartmentByName(name);
            if (departments == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            } else {
                return ResponseEntity.ok(departments);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Departments departments) {
        try {
            Departments department = departmentService.createDepartment(departments);
            if (departments == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            } else {
                return ResponseEntity.ok(department);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDepartment(@PathVariable UUID id, @RequestBody Request request) {
        try {
            if (request.getName() == null || request.getName().isEmpty()) {
                logger.info("Dữ liệu trống");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(null, ActionMessage.SEARCH_NULL));
            } else {
                Departments department = departmentService.updateDepartment(id, request.getName());
                logger.info("Cập nhật thành công");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Response(department, null));
            }
        } catch (Exception e) {
            logger.info("Lỗi Exception");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable UUID id) {
        try {
            departmentService.deleteDepartment(id);
            logger.info("Xóa thành công phòng ban có id: {}", id);
            return ResponseEntity.ok("Xóa phòng ban thành công!");
        } catch (EntityNotFoundException e) {
            logger.warn("Không tìm thấy phòng ban có id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy phòng ban để xóa!");
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}

