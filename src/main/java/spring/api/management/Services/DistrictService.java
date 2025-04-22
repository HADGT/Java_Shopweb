package spring.api.management.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.models.Districts;
import spring.api.management.Respositories.DistrictRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Districts> getAllDistricts() {
        return districtRepository.findAll();
    }

    public Districts getDistrictById(UUID id) {
        return districtRepository.findById(id).orElse(null);
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

    public Districts updateDistrict(UUID id, String name, UUID idParent){
        Districts district = getDistrictById(id);
        if(district == null){
            logger.info("Không tìm thấy dữ liệu District " + id);
            return null;
        }
        if (name == null){
            district.setCityId(id);
        }else if (idParent == null){
            district.setDistrictName(name);
        }else{
            district.setDistrictName(name);
            district.setCityId(id);
        }
        return districtRepository.save(district);
    }
}

