package spring.api.management.model.Services;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.model.DTO.Districts;
import spring.api.management.model.DTO.Locations;
import spring.api.management.model.Respositories.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {
    @Autowired
    private DistrictService districtService;
    @Autowired
    private LocationRepository locationRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Locations> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Locations> getLocationById(UUID id) {
        return locationRepository.findById(id);
    }

    public List<Locations> getLocationByName(String name) {
        return locationRepository.findByNameContaining(name);
    }

    public List<Locations> getLocationByIdParent(UUID id) {
        return locationRepository.findByDistrictId(id);
    }

    public Locations createLocation(Locations locations) {
        return locationRepository.save(locations);
    }

    public void deleteLocation(UUID id) {
        locationRepository.deleteById(id);
    }

    public Locations updateLocation(UUID id, String name, UUID idParent) {
        if (id == null) {
            throw new IllegalArgumentException("ID của Location không được để trống");
        }
        Locations locations = getLocationById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Location với ID: " + id));
        if (name != null && !name.trim().isEmpty()) {
            locations.setStreetAddress(name.trim());
        }
        if (idParent != null) {
            Districts districts = districtService.getDistrictById(idParent)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy District với ID: " + idParent));
            locations.setDistrict(districts);
        }
        return locationRepository.save(locations);
    }
}

