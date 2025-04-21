package spring.api.management.Resource.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import spring.api.management.models.*;

@AllArgsConstructor
@Builder
@Data
public class AddressDto {
    private Countries country;
    private Cities city;
    private Districts district;
    private Locations location;
}
