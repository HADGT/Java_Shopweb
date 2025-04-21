package spring.api.management.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private Countries country;
    private Cities city;
    private Districts district;
    private Locations location;
}
