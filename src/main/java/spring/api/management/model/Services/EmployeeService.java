package spring.api.management.model.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.model.Respositories.CountryRespository;
import spring.api.management.model.Respositories.EmployeeRepository;
import spring.api.management.model.DTO.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CountryRespository countriesRepository;
    @Autowired
    private CityService cityService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private LocationService locationService;

    public List<Employees> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public Employees getEmployeesbyId(UUID Id) {
        return employeeRepository.findById(Id).orElse(null);
    }

    public List<Employees> searchByName(String name) {
        return employeeRepository.searchByName(name);
    }

    public List<Employees> getEmployeesCreatedBetween(LocalDateTime start, LocalDateTime end) {
        return employeeRepository.findByCreatedAtBetween(start, end);
    }

    public List<Employees> getEmployeesUpdateBetween(LocalDateTime start, LocalDateTime end) {
        return employeeRepository.findByUpdatedAtBetween(start, end);
    }

    public List<Employees> findByJobId(UUID id) {
        return employeeRepository.findByJobId(id);
    }

    public List<Employees> findEmployeesByAddress(
            Integer ID,
            UUID CityId,
            UUID DistrictId,
            UUID LocationId
    ) {
        return employeeRepository.findByAddress(ID, CityId, DistrictId, LocationId);
    }

    public List<Employees> findByStatus(boolean status) {
        return employeeRepository.findByStatus(status);
    }

    private Map<String, Object> getOrCreateAddress(Countries country, Cities city, Districts district, Locations location) {
        Map<String, Object> result = new HashMap<>();
        // 1. Country
        if (country == null || country.getCountryKey() == null) return result;
        Countries savedCountry = countriesRepository.findByIdInt(country.getCountryKey().getID())
                .orElseGet(() -> countriesRepository.save(country));
        result.put("country", savedCountry);
        // 2. City
        if (city == null || city.getCityId() == null) return result;
        city.setCountry(savedCountry);
        Cities savedCity = cityService.getCityById(city.getCityId())
                .orElseGet(() -> cityService.createCity(city));
        result.put("city", savedCity);
        // 3. District
        if (district == null || district.getDistrictId() == null) return result;
        district.setCity(savedCity);
        Districts savedDistrict = districtService.getDistrictById(district.getDistrictId())
                .orElseGet(() -> districtService.createDistrict(district));
        result.put("district", savedDistrict);
        // 4. Location
        if (location == null || location.getLocationId() == null) return result;
        location.setDistrict(savedDistrict);
        Locations savedLocation = locationService.getLocationById(location.getLocationId())
                .orElseGet(() -> locationService.createLocation(location));
        result.put("location", savedLocation);
        return result;
    }

    public Employees createEmployee(Employees employee, Countries country, Cities city, Districts district, Locations location) {
        // Giả sử bạn có một phương thức getOrCreateAddress để xử lý việc tìm hoặc tạo các đối tượng địa chỉ
        Map<String, Object> address = getOrCreateAddress(country, city, district, location);
        // Gán đối tượng Locations cho employee
        Locations foundLocation = (Locations) address.get("location");
        if (foundLocation != null) {
            // Gán ID của location vào nhân viên
            employee.setLocation(foundLocation);
        }
        return employeeRepository.save(employee);
    }

    public Employees updateEmployee(UUID id, Employees updatedEmployee, Countries country, Cities city, Districts district, Locations location) {
        Employees existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee == null) return null;
        // Cập nhật thông tin
        if (updatedEmployee.getFirstName() != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        if (updatedEmployee.getLastName() != null) {
            existingEmployee.setLastName(updatedEmployee.getLastName());
        }
        if (updatedEmployee.getEmail() != null) {
            existingEmployee.setEmail(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getPhoneNumber() != null) {
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        }
        if (updatedEmployee.getJobId() != null) {
            existingEmployee.setJobId(updatedEmployee.getJobId());
        }
        if (updatedEmployee.getDescription() != null) {
            existingEmployee.setDescription(updatedEmployee.getDescription());
        }
        // 4. Cập nhật địa chỉ nếu và chỉ nếu có đủ tất cả thông tin
        if (country != null && city != null && district != null && location != null) {
            Map<String, Object> address = getOrCreateAddress(country, city, district, location);
            Locations foundLocation = (Locations) address.get("location");
            if (foundLocation != null) {
                // Gán ID của location vào nhân viên
                existingEmployee.setLocation(foundLocation);
            }
        }
        return employeeRepository.save(existingEmployee);
    }

    public Employees toggleEmployeeStatus(UUID id) {
        Employees employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return null;
        }
        employee.setStatus(!employee.isStatus()); // Đảo trạng thái
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(UUID Id) {
        employeeRepository.deleteById(Id);
    }
}
