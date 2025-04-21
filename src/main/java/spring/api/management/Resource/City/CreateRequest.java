package spring.api.management.Resource.City;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequest {
    private String name;
    private int id;
}
