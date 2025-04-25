package spring.api.management.model.Resource.District;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateRequest {
    private String name;
    private UUID id;
}
