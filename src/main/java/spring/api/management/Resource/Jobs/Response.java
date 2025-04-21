package spring.api.management.Resource.Jobs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.api.management.models.Jobs;

@AllArgsConstructor
@Getter
public class Response {
    private final Jobs jobs;
    private String mess;
}
