package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.model.Resource.Country.Request;
import spring.api.management.model.Resource.Country.Response;
import spring.api.management.model.Services.CountryService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.model.DTO.Countries;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/api/country")
public class CountryController {
    @Autowired
    private CountryService countryService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> getAllCountries() {
        try {
            List<Countries> countries = countryService.getAllCountries();
            return countries.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!") :
                    ResponseEntity.ok(countries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCountriesByName(@RequestParam String name) {
        try {
            List<Countries> countries = countryService.findByNameContaining(name);
            List<Countries> countries1 = countryService.findByIDContaining(name);
            Set<Countries> result = new HashSet<>();
            result.addAll(countries);
            result.addAll(countries1);
            return result.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                    ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: {}" + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCity(@RequestBody Countries countries) {
        try {
            Countries country = countryService.createCountries(countries);
            return (country == null) ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                    ResponseEntity.ok(country);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new spring.api.management.model.Resource.City.Response(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCountry(@PathVariable int id, @RequestBody Request request) {
        try {
            String countryId = request.getCountryId();
            String name = request.getName();
            if (countryId == null || countryId.isEmpty() || name == null || name.isEmpty()) {
                return ResponseEntity.badRequest().body(new Response(null, ActionMessage.SEARCH_FAIL));
            }
            Countries updatedCountry = countryService.updateCountries(id, countryId, name);
            return ResponseEntity.ok(new Response(updatedCountry, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable int id) {
        try {
            countryService.deleteCountries(id);
            logger.info("Xóa thành công job có id: {}", id);
            return ResponseEntity.ok("Xóa job thành công!");
        } catch (EntityNotFoundException e) {
            logger.warn("Không tìm thấy job có id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy job để xóa!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}

