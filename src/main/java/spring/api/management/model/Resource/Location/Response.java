package spring.api.management.model.Resource.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.model.DTO.Locations;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Locations locations;
    private String Mess;
}
