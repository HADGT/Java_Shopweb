package spring.api.management.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.Respositories.CountryRespository;
import spring.api.management.models.Countries;
import spring.api.management.models.CountryKey;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRespository countryRespository;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Countries> getAllCountries() {
        return countryRespository.findAll();
    }

    public Countries getCountriesById(int id) {
        return countryRespository.findByIdInt(id);
    }

    public List<Countries> findByNameContaining(String name) {
        return countryRespository.findByNameContaining(name);
    }

    public List<Countries> findByIDContaining(String id) {
        return countryRespository.findByCountryIdContaining(id);
    }

    public Countries createCountries(Countries country) {
        return countryRespository.save(country);
    }

    public void deleteCountries(int id) {
        countryRespository.deleteById(id);
    }

    public Countries updateCountries(int ID, String id, String name){
        // Kiểm tra xem department có tồn tại không
        Countries existingCountries = getCountriesById(ID);
        if (existingCountries == null) {
            logger.info("Không tìm thấy id city:" + id);
            return null;
        } else {
            existingCountries.setCountryName(name);
            // Tạo mới khóa chính nhúng (CountryKey)
            CountryKey newKey = new CountryKey(ID, id.toUpperCase());
            existingCountries.setCountryKey(newKey);
            return countryRespository.save(existingCountries);
        }
    }
}
