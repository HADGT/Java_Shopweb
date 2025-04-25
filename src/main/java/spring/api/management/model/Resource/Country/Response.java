package spring.api.management.model.Resource.Country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.model.DTO.Countries;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Countries countries;
    private String Mess;
}
