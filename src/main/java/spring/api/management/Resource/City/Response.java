package spring.api.management.Resource.City;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.models.Cities;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Cities city;
    private String Mess;
}
