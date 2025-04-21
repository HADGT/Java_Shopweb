//package spring.api.management.Services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import spring.api.management.models.Districts;
//import spring.api.management.Respositories.DistrictRepository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class DistrictService {
//
//    @Autowired
//    private DistrictRepository districtRepository;
//
//    public List<Districts> getAllRegions() {
//        return districtRepository.findAll();
//    }
//
//    public Districts getRegionById(UUID id) {
//        return districtRepository.findById(id).orElse(null);
//    }
//
//    public Districts createRegion(Districts districts) {
//        return districtRepository.save(districts);
//    }
//
//    public void deleteRegion(UUID id) {
//        districtRepository.deleteById(id);
//    }
//}
//
