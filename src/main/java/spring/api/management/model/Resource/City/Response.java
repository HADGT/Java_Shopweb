package spring.api.management.model.Resource.City;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.model.DTO.Cities;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Cities city;
    private String Mess;
}
