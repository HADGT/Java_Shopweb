package spring.api.management.model.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.model.Respositories.CountryRespository;
import spring.api.management.model.DTO.Countries;
import spring.api.management.model.DTO.CountryKey;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRespository countryRespository;
    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Countries> getAllCountries() {
        return countryRespository.findAll();
    }

    public Optional<Countries> getCountriesById(int id) {
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
        Optional<Countries> existingCountries = getCountriesById(ID);
        if (existingCountries.isEmpty()) { // Sử dụng isPresent() thay vì kiểm tra null
            logger.info("Không tìm thấy id city: {}", id);
            return null;
        } else {
            Countries country = existingCountries.get(); // Lấy đối tượng thực tế từ Optional
            country.setCountryName(name);
            // Tạo mới khóa chính nhúng (CountryKey)
            CountryKey newKey = new CountryKey(ID, id.toUpperCase());
            country.setCountryKey(newKey);
            // Lưu lại thông tin vào repository
            return countryRespository.save(country);
        }
    }
}
