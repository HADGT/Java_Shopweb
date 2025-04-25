package spring.api.management.model.Resource.Jobs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.api.management.model.DTO.Jobs;

@AllArgsConstructor
@Getter
public class Response {
    private final Jobs jobs;
    private String mess;
}
