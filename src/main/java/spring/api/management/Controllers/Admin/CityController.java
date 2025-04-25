package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.model.Resource.City.CreateRequest;
import spring.api.management.model.Resource.City.Request;
import spring.api.management.model.Resource.City.Response;
import spring.api.management.model.Respositories.CityRepository;
import spring.api.management.model.Services.CityService;
import spring.api.management.model.Services.CountryService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.model.DTO.Cities;
import spring.api.management.model.DTO.Countries;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/city")
public class CityController {
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public ResponseEntity<?> getAllRegions() {
        try {
            List<Cities> cities = cityService.getAllCities();
            return cities.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!") :
                    ResponseEntity.ok(cities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCityByName(@RequestParam String name) {
        try {
            List<Cities> cities = cityService.findByNameContaining(name);
            return (cities == null || cities.isEmpty()) ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                    ResponseEntity.ok(cities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> findCitiesByCountry(int id) {
        try {
            Optional<Countries> country = countryService.getCountriesById(id);
            if (country.isPresent()) {
                Countries getCountry = country.get();
                List<Cities> cities = cityService.findByCountryIDContaining(getCountry.getCountryKey().getCountryId());

                return (cities == null || cities.isEmpty()) ?
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                        ResponseEntity.ok(cities);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy quốc gia với ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Response> createCity(@RequestBody CreateRequest request) {
        try {
            if (request.getName() == null || request.getName().isEmpty()) {
                logger.info("Dữ liệu trống");
                return ResponseEntity.badRequest()
                        .body(new Response(null, ActionMessage.SEARCH_NULL));
            } else {
                Optional<Countries> countryOptional = countryService.getCountriesById(request.getId());
                return countryOptional
                        .map(country -> {
                            Cities city = new Cities();
                            city.setCityName(request.getName());
                            city.setCountry(country);
                            Cities createdCity = cityService.createCity(city);
                            return ResponseEntity.ok(new Response(createdCity, ActionMessage.CREATE_SUCCESS));
                        })
                        .orElseGet(() -> {
                            logger.info("Không tìm thấy quốc gia theo ID");
                            return ResponseEntity.badRequest().body(new Response(null, "Không tìm thấy quốc gia theo ID"));
                        });
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCity(@PathVariable UUID id, @RequestParam(required = false) Integer IDCountry, @RequestBody Request request) {
        try {
            String name = request.getName();
            boolean isNameValid = name != null && !name.isEmpty();
            if (!isNameValid && IDCountry == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(null, ActionMessage.UPDATE_FAILED));
            } else {
                Cities city;
                Integer countryId = null;
                if (IDCountry != null) {
                    Optional<Countries> countryOptional = countryService.getCountriesById(IDCountry);
                    if (countryOptional.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response(null, "Không tìm thấy quốc gia"));
                    } else {
                        Countries country = countryOptional.get();
                        countryId = country.getCountryKey().getID();
                    }
                }
                Cities updatedCity = cityService.updateCity(id, isNameValid ? name : null, countryId != null ? countryId : 0);
                logger.info("Cập nhật thành công");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Response(updatedCity, ActionMessage.UPDATE_SUCCESS));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable UUID id) {
        try {
            cityService.deleteCity(id);
            logger.info("Xóa thành công City có id: {}", id);
            return ResponseEntity.ok("Xóa City thành công!");
        } catch (EntityNotFoundException e) {
            logger.warn("Không tìm thấy City có id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy City để xóa!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}

