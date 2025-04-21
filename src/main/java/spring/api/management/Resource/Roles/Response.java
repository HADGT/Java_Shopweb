package spring.api.management.Resource.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.api.management.models.Roles;

@AllArgsConstructor
@Getter
public class Response {
    private final Roles role;
    private String mess;
}