package spring.api.management.model.Services;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.model.Respositories.DistrictRepository;
import spring.api.management.model.DTO.Cities;
import spring.api.management.model.DTO.Districts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private CityService cityService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Districts> getAllDistricts() {
        return districtRepository.findAll();
    }

    public Optional<Districts> getDistrictById(UUID id) {
        return districtRepository.findById(id);
    }

    public List<Districts> getDistrictByName(String name){
        return districtRepository.findByNameContaining(name);
    }

    public List<Districts> getDistrictByIdParent(UUID id){
        return districtRepository.findByCityId(id);
    }

    public Districts createDistrict(Districts districts) {
        return districtRepository.save(districts);
    }

    public void deleteDistrict(UUID id) {
        districtRepository.deleteById(id);
    }

    public Districts updateDistrict(UUID id, String name, UUID cityId) {
        if (id == null) {
            throw new IllegalArgumentException("ID của District không được để trống");
        }
        Districts district = getDistrictById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy District với ID: " + id));
        if (name != null && !name.trim().isEmpty()) {
            district.setDistrictName(name);
        }
        if (cityId != null) {
            Cities city = cityService.getCityById(cityId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy City với ID: " + cityId));
            district.setCity(city);
        }
        // Lưu và trả về kết quả
        return districtRepository.save(district);
    }
}

