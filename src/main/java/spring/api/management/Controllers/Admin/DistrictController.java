package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.Resource.City.Request;
import spring.api.management.Resource.District.CreateRequest;
import spring.api.management.Resource.District.Response;
import spring.api.management.Services.CityService;
import spring.api.management.Services.DistrictService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.models.Cities;
import spring.api.management.models.Districts;

import java.util.List;
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
            if (districts.isEmpty()) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!");
            }
            return ResponseEntity.ok(districts);
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getDistrictByName(@RequestParam String name) {
        try {
            List<Districts> districts = districtService.getDistrictByName(name);
            if (districts == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            } else {
                return ResponseEntity.ok(districts);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> findDistrictByCity(UUID id) {
        try {
            Cities city = cityService.getCityById(id);
            List<Districts> districts = districtService.getDistrictByIdParent(city.getCityId());
            if (districts == null) {
                logger.warn("Lỗi: Danh sách trống!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!");
            } else {
                return ResponseEntity.ok(districts);
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
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
                Cities city = cityService.getCityById(request.getId());
                if (city == null) {
                    return ResponseEntity.badRequest().body(new Response(null, "Không tìm thấy quốc gia theo ID"));
                } else {
                    Districts district = new Districts();
                    district.setDistrictName(request.getName());
                    district.setCityId(request.getId());
                    district.setCityId(city.getCityId());
                    Districts districtAfter = districtService.createDistrict(district);
                    return ResponseEntity.ok(new Response(districtAfter, ActionMessage.CREATE_SUCCESS));
                }
            }
        } catch (Exception e) {
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDistrict(@PathVariable UUID id, @RequestParam(required = false) UUID IDCity, @RequestBody Request request) {
        try {
            if ((IDCity == null) && (request.getName() == null || request.getName().isEmpty())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(null, ActionMessage.UPDATE_FAILED));
            } else {
                Districts district;
                if (request.getName() == null || request.getName().isEmpty()) {
                    Cities city = cityService.getCityById(IDCity);
                    if (city == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, "Không tìm thấy quốc gia"));
                    }
                    district = districtService.updateDistrict(id, null, city.getCityId());
                } else if (IDCity == null) {
                    district = districtService.updateDistrict(id, request.getName(), null);
                } else {
                    Cities city = cityService.getCityById(IDCity);
                    if (city == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, "Không tìm thấy quốc gia"));
                    }
                    district = districtService.updateDistrict(id, request.getName(), city.getCityId());
                }
                logger.info("Cập nhật thành công");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Response(district, ActionMessage.UPDATE_SUCCESS));
            }
        } catch (
                Exception e) {
            logger.info("Lỗi Exception", e);
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
            logger.error("Lỗi máy chủ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }
}

