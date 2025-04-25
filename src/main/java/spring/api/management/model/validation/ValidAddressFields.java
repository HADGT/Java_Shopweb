package spring.api.management.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AddressFieldsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAddressFields {
    String message() default "Nếu 1 trong 4 trường địa chỉ được nhập thì tất cả đều phải được nhập!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}