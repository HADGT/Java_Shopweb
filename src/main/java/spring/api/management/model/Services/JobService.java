package spring.api.management.model.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.model.DTO.Departments;
import spring.api.management.model.DTO.Jobs;
import spring.api.management.model.Respositories.JobRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    private List<Departments> departmentList = new ArrayList<>();
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Jobs> getAllJobs() {
        return jobRepository.findAll();
    }

    public Jobs getJobById(UUID id) {
        return jobRepository.findById(id).orElse(null);
    }

    public List<Jobs> getJobyName(String Title) {
        return jobRepository.findByNameContaining(Title);
    }

    public List<Jobs> getJobBySalary(BigDecimal x, BigDecimal y) {
        if(y.compareTo(x) >= 0){
            return jobRepository.JOBS_LIST(x, y);
        } else {
            logger.info("Dữ liệu y phải lớn hơn hoặc bằng x!");
            return null;
        }
    }

    public Jobs createJob(Jobs jobs) {
        return jobRepository.save(jobs);
    }

    public Jobs updateJobs(UUID id, String title, BigDecimal maxSalary, BigDecimal minSalary) {
        Jobs CheckValue = getJobById(id);
        if (CheckValue == null) {
            logger.info("Dữ liệu công việc không tồn tại trong hệ thống!");
            return null;
        } else {
            if(title != null){
                CheckValue.setJobTitle(title);
            }
            if(maxSalary != null){
                CheckValue.setMaxSalary(maxSalary);
            }
            if(minSalary != null){
                CheckValue.setMinSalary(minSalary);
            }
            logger.info("Cập nhật dữ liệu thành công!");
            return jobRepository.save(CheckValue);
        }
    }

    public void deleteJob(UUID id) {
        jobRepository.deleteById(id);
    }
}

