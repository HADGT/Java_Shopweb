package spring.api.management.Resource.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.api.management.models.Departments;

@AllArgsConstructor
@Builder
public class Response {
    @Getter
    private final Departments Departments;
    private String Mess;
}
