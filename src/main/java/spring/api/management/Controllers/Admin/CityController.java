package spring.api.management.Controllers.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.Resource.City.CreateRequest;
import spring.api.management.Resource.City.Request;
import spring.api.management.Resource.City.Response;
import spring.api.management.Respositories.CityRepository;
import spring.api.management.Services.CityService;
import spring.api.management.Services.CountryService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.models.Cities;
import spring.api.management.models.Countries;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/city")
public class CityController {
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public ResponseEntity<?> getAllRegions() {
        try {
            List<Cities> cities = cityService.getAllCities();
            if (cities == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!");
            } else {
                return ResponseEntity.ok(cities);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCityByName(@RequestParam String name) {
        try {
            List<Cities> cities = cityService.findByNameContaining(name);
            if (cities == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            } else {
                return ResponseEntity.ok(cities);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> findCitiesByCountry(int id) {
        try {
            Countries country = countryService.getCountriesById(id);
            List<Cities> cities = cityService.findByCountryIDContaining(country.getCountryKey().getCountryId());
            if (cities == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            } else {
                return ResponseEntity.ok(cities);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Response> createCity(@RequestBody CreateRequest request) {
        try {
            if (request.getName() == null || request.getName().isEmpty()) {
                logger.info("Dữ liệu trống");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(null, ActionMessage.SEARCH_NULL));
            } else {
                Countries country = countryService.getCountriesById(request.getId());
                if (country == null) {
                    return ResponseEntity.badRequest().body(new Response(null, "Không tìm thấy quốc gia theo ID"));
                } else {
                    Cities city = new Cities();
                    city.setCityName(request.getName());
                    city.setID(request.getId());
                    city.setCountryId(country.getCountryKey().getCountryId());
                    Cities cityAfter = cityService.createCity(city);
                    return ResponseEntity.ok(new Response(cityAfter, ActionMessage.CREATE_SUCCESS));
                }
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDepartment(@PathVariable UUID id, @RequestParam(required = false) Integer IDCountry, @RequestBody Request request) {
        try {
            if ((IDCountry == null) && (request.getName() == null || request.getName().isEmpty())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(null, ActionMessage.UPDATE_FAILED));
            } else {
                Cities city;
                if (request.getName() == null || request.getName().isEmpty()) {
                    Countries country = countryService.getCountriesById(IDCountry);
                    if (country == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, "Không tìm thấy quốc gia"));
                    }
                    city = cityService.updateCity(id, null, country.getCountryKey().getID());
                } else if (IDCountry == null) {
                    city = cityService.updateCity(id, request.getName(), 0);  // Giữ nguyên country
                } else {
                    Countries country = countryService.getCountriesById(IDCountry);
                    if (country == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, "Không tìm thấy quốc gia"));
                    }
                    city = cityService.updateCity(id, request.getName(), country.getCountryKey().getID());
                }
                logger.info("Cập nhật thành công");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Response(city, ActionMessage.UPDATE_SUCCESS));
            }
        } catch (
                Exception e) {
            logger.info("Lỗi Exception", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable UUID id) {
        try {
            cityService.deleteCity(id);
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

