package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.Resource.Jobs.Request;
import spring.api.management.Resource.Jobs.Response;
import spring.api.management.Services.JobService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.models.Jobs;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/job")
public class JobController {
    @Autowired
    private JobService jobService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> getAllJobs() {
        try {
            List<Jobs> jobs = jobService.getAllJobs();
            if (jobs.isEmpty()) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!");
            }
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/Search")
    public ResponseEntity<?> getJobByName(@RequestParam String title) {
        try {
            List<Jobs> jobs = jobService.getJobyName(title);
            if (jobs.isEmpty()) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            }
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: {}" + e.getMessage());
        }
    }

    @GetMapping("/filter-by-price")
    public ResponseEntity<?> getJobBySalary(@RequestParam(required = false) BigDecimal x, @RequestParam(required = false) BigDecimal y) {
        try {
            if (x == null) {
                return ResponseEntity.badRequest().body("Lỗi: x không được để trống!" + x);
            }
            if (y == null) {
                return ResponseEntity.badRequest().body("Lỗi: y không được để trống!" + y);
            }
            if (x.compareTo(BigDecimal.ZERO) < 0) {
                return ResponseEntity.badRequest().body("Lỗi: Min salary không thể âm! x = " + x);
            }
            if (x.compareTo(BigDecimal.ZERO) < 0) {
                return ResponseEntity.badRequest().body("Lỗi: Min salary không thể âm! x = " + x);
            }
            if (y.compareTo(BigDecimal.ZERO) < 0) {
                return ResponseEntity.badRequest().body("Lỗi: Max salary không thể âm! y = " + y);
            }
            if (x.compareTo(y) > 0) {
                return ResponseEntity.badRequest().body("Lỗi: Min salary không thể lớn hơn max salary! x = " + x + ", y = " + y);
            }
            List<Jobs> jobs = jobService.getJobBySalary(x, y);
            if (jobs == null || jobs.isEmpty()) {
                logger.warn("Không tìm thấy công việc nào trong khoảng lương từ {} đến {}", x, y);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy công việc nào trong khoảng lương từ " + x + " đến " + y);
            }
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: {}" + e.getMessage());
        }
    }

    @PostMapping
    public Jobs createJob(@RequestBody Jobs job) {
        return jobService.createJob(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateJob(@PathVariable UUID id, @RequestBody Request request) {
        try {
            if (request.getTitle() == null || request.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(null, ActionMessage.SEARCH_FAIL));
            } else {
                Jobs job = jobService.updateJobs(id, request.getTitle(), request.getMaxSalary(), request.getMinSalary());
                return ResponseEntity.status(HttpStatus.OK).body(new Response(job, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable UUID id) {
        try {
            jobService.deleteJob(id);
            logger.info("Xóa thành công job có id: {}", id);
            return ResponseEntity.ok("Xóa job thành công!");
        } catch (EntityNotFoundException e) {
            logger.warn("Không tìm thấy job có id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy job để xóa!");
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}

