package spring.api.management.Resource.District;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.models.Districts;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Districts districts;
    private String Mess;
}
