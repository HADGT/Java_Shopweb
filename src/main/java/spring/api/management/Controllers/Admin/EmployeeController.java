package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.model.Resource.register.RegisterRequest;
import spring.api.management.model.Resource.register.RegisterResponse;
import spring.api.management.model.Resource.register.UpdateRequest;
import spring.api.management.helper.ActionMessage;
import spring.api.management.model.DTO.*;
import spring.api.management.model.Services.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CityService cityService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private LocationService locationService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<Employees> employees = employeeService.getAllEmployee();
            return employees.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!") :
                    ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String name) {
        try {
            List<Employees> employees = employeeService.searchByName(name);
            return (employees == null || employees.isEmpty()) ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                    ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/created-between")
    public ResponseEntity<?> getEmployeesCreatedBetween(
            @RequestParam(value = "start", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(value = "end", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        if (start == null || end == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Vui lòng cung cấp đầy đủ tham số 'start' và 'end' theo định dạng ISO_DATE_TIME.");
        }
        List<Employees> employees = employeeService.getEmployeesCreatedBetween(start, end);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/updated-between")
    public ResponseEntity<?> getEmployeesUpdatedBetween(
            @RequestParam(value = "start", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(value = "end", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        if (start == null || end == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Vui lòng cung cấp đầy đủ tham số 'start' và 'end' theo định dạng ISO_DATE_TIME.");
        }
        List<Employees> employees = employeeService.getEmployeesUpdateBetween(start, end);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/select/job")
    public ResponseEntity<?> findByJobId(UUID id) {
        try {
            Jobs jobs = jobService.getJobById(id);
            if (jobs == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm ID Job cần tìm!");
            } else {
                List<Employees> employees = employeeService.findByJobId(jobs.getJobId());
                return (employees == null || employees.isEmpty()) ?
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                        ResponseEntity.ok(employees);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search-by-address")
    public ResponseEntity<?> searchByAddress(
            @RequestParam(required = false) Integer countryId,
            @RequestParam(required = false) UUID cityId,
            @RequestParam(required = false) UUID districtId,
            @RequestParam(required = false) UUID locationId
    ) {
        try {
            List<Employees> employees = employeeService.findEmployeesByAddress(countryId, cityId, districtId, locationId);
            return (employees == null || employees.isEmpty()) ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                    ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/select/status")
    public ResponseEntity<?> findByStatus(UUID id, boolean status) {
        try {
            Employees employee = employeeService.getEmployeesbyId(id);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm ID nhân viên cần tìm!");
            } else {
                List<Employees> employees = employeeService.findByStatus(status);
                return (employees == null || employees.isEmpty()) ?
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                        ResponseEntity.ok(employees);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PatchMapping("/status")
    public ResponseEntity<?> toggleStatus(@PathVariable UUID id) {
        Employees updated = employeeService.toggleEmployeeStatus(id);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ActionMessage.UPDATE_FAILED);
        }
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerEmployee(@Valid @RequestBody RegisterRequest request) {
        Employees employee = new Employees();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setJobId(request.getJobId());
        employee.setPass(request.getPass());
        Countries getCountry = null;
        Cities getCity = null;
        Districts getDistrict = null;
        Locations getLocation = null;
        Optional<Countries> country = countryService.getCountriesById(request.getCountryID());
        if(country.isPresent()) {
            getCountry = country.get();
        }
        Optional<Cities> city = cityService.getCityById(request.getCityID());
        if(city.isPresent()) {
            getCity = city.get();
        }
        Optional<Districts> district = districtService.getDistrictById(request.getDistrictID());
        if(district.isPresent()) {
            getDistrict = district.get();
        }
        Optional<Locations> location = locationService.getLocationById(request.getLocationID());
        if(location.isPresent()) {
            getLocation = location.get();
        }
        Employees saved = employeeService.createEmployee(employee, getCountry, getCity, getDistrict, getLocation);
        return ResponseEntity.status(HttpStatus.OK).body(new RegisterResponse(employee, "Đăng ký thành công!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegisterResponse> updateEmployee(@PathVariable UUID id, @RequestBody UpdateRequest request) {
        try {
            Employees updated = new Employees();
            updated.setFirstName(request.getFirstName());
            updated.setLastName(request.getLastName());
            updated.setEmail(request.getEmail());
            updated.setPhoneNumber(request.getPhoneNumber());
            updated.setJobId(request.getJobId());
            updated.setDescription(request.getDescription());
            Countries getCountry = null;
            Cities getCity = null;
            Districts getDistrict = null;
            Locations getLocation = null;
            Optional<Countries> country = countryService.getCountriesById(request.getCountryID());
            if(country.isPresent()) {
                getCountry = country.get();
            }
            Optional<Cities> city = cityService.getCityById(request.getCityID());
            if(city.isPresent()) {
                getCity = city.get();
            }
            Optional<Districts> district = districtService.getDistrictById(request.getDistrictID());
            if(district.isPresent()) {
                getDistrict = district.get();
            }
            Optional<Locations> location = locationService.getLocationById(request.getLocationID());
            if(location.isPresent()) {
                getLocation = location.get();
            }
            Employees result = employeeService.updateEmployee(id, updated, getCountry, getCity, getDistrict, getLocation);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RegisterResponse(null, ActionMessage.UPDATE_FAILED));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new RegisterResponse(result, ActionMessage.UPDATE_SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RegisterResponse(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable UUID id) {
        try {
            employeeService.deleteEmployee(id);
            logger.info("Xóa thành công phòng ban có id: {}", id);
            return ResponseEntity.ok("Xóa phòng ban thành công!");
        } catch (EntityNotFoundException e) {
            logger.warn("Không tìm thấy phòng ban có id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy phòng ban để xóa!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}
