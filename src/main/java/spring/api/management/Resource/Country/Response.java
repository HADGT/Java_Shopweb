package spring.api.management.Resource.Country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.models.Countries;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Countries countries;
    private String Mess;
}
