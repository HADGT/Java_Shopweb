package spring.api.management.model.Resource.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.model.DTO.Employees;

@AllArgsConstructor
@Builder
public class RegisterResponse {
    @Getter
    private final Employees employees;
    private String Mess;
}
