//package spring.api.management.Services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import spring.api.management.models.Locations;
//import spring.api.management.Respositories.LocationRepository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class LocationService {
//
//    @Autowired
//    private LocationRepository locationRepository;
//
//    public List<Locations> getAllRegions() {
//        return locationRepository.findAll();
//    }
//
//    public Locations getRegionById(UUID id) {
//        return locationRepository.findById(id).orElse(null);
//    }
//
//    public Locations createRegion(Locations locations) {
//        return locationRepository.save(locations);
//    }
//
//    public void deleteRegion(UUID id) {
//        locationRepository.deleteById(id);
//    }
//}
//
