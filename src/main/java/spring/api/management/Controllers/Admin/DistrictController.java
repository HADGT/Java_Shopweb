package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.model.Resource.City.Request;
import spring.api.management.model.Resource.District.CreateRequest;
import spring.api.management.model.Resource.District.Response;
import spring.api.management.model.Services.CityService;
import spring.api.management.model.Services.DistrictService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.model.DTO.Cities;
import spring.api.management.model.DTO.Districts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/districts")
public class DistrictController {
    @Autowired
    private DistrictService districtService;
    @Autowired
    private CityService cityService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> getAllDistricts() {
        try {
            List<Districts> districts = districtService.getAllDistricts();
            return districts.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!") :
                    ResponseEntity.ok(districts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getDistrictByName(@RequestParam String name) {
        try {
            List<Districts> districts = districtService.getDistrictByName(name);
            return (districts == null || districts.isEmpty()) ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                    ResponseEntity.ok(districts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> findDistrictByCity(UUID id) {
        try {
            Optional<Cities> city = cityService.getCityById(id);
            if (city.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm ID City cần tìm!");
            } else {
                Cities getCity = city.get();
                List<Districts> districts = districtService.getDistrictByIdParent(getCity.getCityId());
                return (districts == null || districts.isEmpty()) ?
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                        ResponseEntity.ok(districts);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Response> createDistrict(@RequestBody CreateRequest request) {
        try {
            if (request.getName() == null || request.getName().isEmpty()) {
                logger.info("Dữ liệu trống");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(null, ActionMessage.SEARCH_NULL));
            } else {
                return cityService.getCityById(request.getId())
                        .map(cities -> {
                            Districts district = new Districts();
                            district.setDistrictName(request.getName());
                            district.setCity(cities);
                            Districts districtAfter = districtService.createDistrict(district);
                            return ResponseEntity.ok(new Response(districtAfter, ActionMessage.CREATE_SUCCESS));
                        })
                        .orElseGet(() -> ResponseEntity.badRequest().body(new Response(null, "Không tìm thấy thành phố theo ID")));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDistrict(@PathVariable UUID id, @RequestParam(required = false) UUID IDCity, @RequestBody Request request) {
        try {
            String name = request.getName();
            boolean isNameValid = name != null && !name.isEmpty();
            if (!isNameValid && IDCity == null) {
                return ResponseEntity.badRequest().body(new Response(null, ActionMessage.UPDATE_FAILED));
            } else {
                UUID cityId = null;
                if (IDCity != null) {
                    Optional<Cities> city = cityService.getCityById(IDCity);
                    if (city.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response(null, "Không tìm thấy quốc gia"));
                    }
                    Cities existingCity = city.get();
                    cityId = existingCity.getCityId();
                }
                Districts updatedDistrict = districtService.updateDistrict(id, isNameValid ? name : null, cityId);
                logger.info("Cập nhật thành công");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Response(updatedDistrict, ActionMessage.UPDATE_SUCCESS));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDistrict(@PathVariable UUID id) {
        try {
            districtService.deleteDistrict(id);
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

