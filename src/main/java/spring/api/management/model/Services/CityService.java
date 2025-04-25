package spring.api.management.model.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.model.Respositories.CountryRespository;
import spring.api.management.model.DTO.Cities;
import spring.api.management.model.Respositories.CityRepository;
import spring.api.management.model.DTO.Countries;

import java.util.List;
import java.util.Optional;
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

    public Optional<Cities> getCityById(UUID id) {
        return cityRepository.findById(id);
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
        Optional<Cities> existingCityOpt = getCityById(id);
        if (existingCityOpt.isEmpty()) {
            logger.info("Không tìm thấy city với ID: {}", id);
            return null;
        }
        Cities existingCity = existingCityOpt.get();
        if (name != null) {
            existingCity.setCityName(name);
        }
        if (ID > 0) {
            Optional<Countries> countryOpt = countryRespository.findByIdInt(ID);
            if (countryOpt.isPresent()) {
                Countries country = countryOpt.get();
                existingCity.setCountry(country);
            } else {
                logger.info("Không tìm thấy country với ID: {}", ID);
                return null;
            }
        }
        return cityRepository.save(existingCity);
    }
}
