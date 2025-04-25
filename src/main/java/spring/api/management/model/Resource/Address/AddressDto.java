package spring.api.management.model.Resource.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import spring.api.management.model.DTO.Cities;
import spring.api.management.model.DTO.Countries;
import spring.api.management.model.DTO.Districts;
import spring.api.management.model.DTO.Locations;

@AllArgsConstructor
@Builder
@Data
public class AddressDto {
    private Countries country;
    private Cities city;
    private Districts district;
    private Locations location;
}
