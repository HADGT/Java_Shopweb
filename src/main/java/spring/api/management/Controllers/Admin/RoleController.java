package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.Resource.Roles.Request;
import spring.api.management.Resource.Roles.Response;
import spring.api.management.Services.RoleService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.models.Roles;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Roles> roles = roleService.getAllRoles();
            if (roles.isEmpty()) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!");
            }
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/Search")
    public ResponseEntity<?> getRoleByName(@RequestParam String name) {
        try {
            List<Roles> roles = roleService.getRoleByName(name);
            if (roles.isEmpty()) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            }
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: {}" + e.getMessage());
        }
    }

    @PostMapping
    public Roles createRoles(@RequestBody Roles roles) {
        return roleService.createRoles(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateJob(@PathVariable UUID id, @RequestBody Request request) {
        try {
            if (request.getName() == null || request.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(null, ActionMessage.SEARCH_FAIL));
            } else {
                Roles role = roleService.updateJobs(id, request.getName());
                return ResponseEntity.status(HttpStatus.OK).body(new Response(role, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoles(@PathVariable UUID id) {
        try {
            roleService.deleteRoles(id);
            logger.info("Xóa thành công role có id: {}", id);
            return ResponseEntity.ok("Xóa role thành công!");
        } catch (EntityNotFoundException e) {
            logger.warn("Không tìm thấy role có id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy role để xóa!");
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}

