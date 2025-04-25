package spring.api.management.model.Resource.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.api.management.model.DTO.Roles;

@AllArgsConstructor
@Getter
public class Response {
    private final Roles role;
    private String mess;
}