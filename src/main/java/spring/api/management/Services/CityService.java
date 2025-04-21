package spring.api.management.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.Respositories.CountryRespository;
import spring.api.management.models.Cities;
import spring.api.management.Respositories.CityRepository;
import spring.api.management.models.Countries;

import java.util.List;
import java.util.UUID;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRespository countryRespository;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Cities> getAllCities() {
        return cityRepository.findAll();
    }

    public Cities getCityById(UUID id) {
        return cityRepository.findById(id).orElse(null);
    }

    public List<Cities> findByNameContaining(String name) {
        return cityRepository.findByNameContaining(name);
    }

    public List<Cities> findByCountryIDContaining(String id) {
        return cityRepository.findByCountryIDContaining(id);
    }

    public Cities createCity(Cities city) {
        return cityRepository.save(city);
    }

    public void deleteCity(UUID id) {
        cityRepository.deleteById(id);
    }

    public Cities updateCity(UUID id, String name, int ID) {
        // Tìm city theo ID
        Cities existingCities = getCityById(id);
        if (existingCities == null) {
            logger.info("Không tìm thấy city với ID: " + id);
            return null;
        }
        if (name != null) {
            existingCities.setCityName(name);
        }
        if (ID > 0) {
            Countries country = countryRespository.findByIdInt(ID);
            if (country != null) {
                existingCities.setID(ID);
                existingCities.setCountryId(country.getCountryKey().getCountryId());
            }
        }
        return cityRepository.save(existingCities);
    }
}
