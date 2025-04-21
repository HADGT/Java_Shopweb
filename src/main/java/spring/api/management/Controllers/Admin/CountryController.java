package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.Resource.Country.Request;
import spring.api.management.Resource.Country.Response;
import spring.api.management.Services.CountryService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.models.Countries;

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
            if (countries.isEmpty()) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!");
            }
            return ResponseEntity.ok(countries);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
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
            if (result.isEmpty()) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: {}" + e.getMessage());
        }
    }

    @PostMapping
    public Countries createCountry(@RequestBody Countries countries) {
        return countryService.createCountries(countries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCountry(@PathVariable int id, @RequestBody Request request) {
        try {
            if (request.getCountryId() == null || request.getCountryId().isEmpty() || request.getName() == null || request.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(null, ActionMessage.SEARCH_FAIL));
            } else {
                Countries country = countryService.updateCountries(id, request.getCountryId(), request.getName());
                return ResponseEntity.status(HttpStatus.OK).body(new Response(country, null));
            }
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
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}

