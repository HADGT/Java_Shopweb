package spring.api.management.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.models.Locations;
import spring.api.management.Respositories.LocationRepository;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Locations> getAllLocations() {
        return locationRepository.findAll();
    }

    public Locations getLocationById(UUID id) {
        return locationRepository.findById(id).orElse(null);
    }

    public List<Locations> getLocationByName(String name){
        return locationRepository.findByNameContaining(name);
    }

    public List<Locations> getLocationByIdParent(UUID id){
        return locationRepository.findByDistrictId(id);
    }

    public Locations createLocation(Locations locations) {
        return locationRepository.save(locations);
    }

    public void deleteLocation(UUID id) {
        locationRepository.deleteById(id);
    }

    public Locations updateLocation(UUID id, String name, UUID idParent){
        Locations location = getLocationById(id);
        if(location == null){
            logger.info("Không tìm thấy dữ liệu District " + id);
            return null;
        }
        if (name == null){
            location.setDistrictId(id);
        }else if (idParent == null){
            location.setStreetAddress(name);
        }else{
            location.setStreetAddress(name);
            location.setDistrictId(id);
        }
        return locationRepository.save(location);
    }
}

