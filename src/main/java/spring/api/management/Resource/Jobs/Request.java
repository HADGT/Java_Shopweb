package spring.api.management.Resource.Jobs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Request {
    private String Title;
    private BigDecimal MinSalary;
    private BigDecimal MaxSalary;
}
