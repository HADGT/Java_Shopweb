package spring.api.management.model.Resource.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.model.DTO.Departments;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Departments Departments;
    private String Mess;
}
