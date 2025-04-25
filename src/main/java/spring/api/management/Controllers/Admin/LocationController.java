package spring.api.management.Controllers.Admin;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.management.model.Resource.City.Request;
import spring.api.management.model.Resource.Location.CreateRequest;
import spring.api.management.model.Resource.Location.Response;
import spring.api.management.model.Services.DistrictService;
import spring.api.management.model.Services.LocationService;
import spring.api.management.helper.ActionMessage;
import spring.api.management.model.DTO.Districts;
import spring.api.management.model.DTO.Locations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private DistrictService districtService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        try {
            List<Locations> locations = locationService.getAllLocations();
            return locations.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh sách trống!") :
                    ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getLocationByName(@RequestParam String name) {
        try {
            List<Locations> location = locationService.getLocationByName(name);
            return (location == null || location.isEmpty()) ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy kết quả cần tìm!") :
                    ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Response> createLocation(@RequestBody CreateRequest request) {
        try {
            if (request.getName() == null || request.getName().isEmpty()) {
                logger.info("Dữ liệu trống");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(null, ActionMessage.SEARCH_NULL));
            } else {
                return districtService.getDistrictById(request.getId())
                        .map(district -> {
                            Locations location = new Locations();
                            location.setStreetAddress(request.getName());
                            location.setDistrict(district); // Truy cập getDistrictId() trên đối tượng District
                            Locations locationAfter = locationService.createLocation(location);
                            return ResponseEntity.ok(new Response(locationAfter, ActionMessage.CREATE_SUCCESS));
                        })
                        .orElseGet(() -> ResponseEntity.badRequest().body(new Response(null, "Không tìm thấy khu vực theo ID")));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateLocation(@PathVariable UUID id, @RequestParam(required = false) UUID IDDistrict, @RequestBody Request request) {
        try {
            String name = request.getName();
            boolean isNameValid = name != null && !name.isEmpty();
            if (!isNameValid && IDDistrict == null) {
                return ResponseEntity.badRequest().body(new Response(null, ActionMessage.UPDATE_FAILED));
            } else {
                UUID districtId = null;
                if (IDDistrict != null) {
                    Optional<Districts> district = districtService.getDistrictById(IDDistrict);
                    if (district.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response(null, "Không tìm thấy quốc gia"));
                    } else {
                        Districts getDistrict = district.get();
                        districtId = getDistrict.getDistrictId();
                    }
                }
                Locations updatedLocation = locationService.updateLocation(id, isNameValid ? name : null, districtId);
                logger.info("Cập nhật thành công");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Response(updatedLocation, ActionMessage.UPDATE_SUCCESS));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable UUID id) {
        try {
            locationService.deleteLocation(id);
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

