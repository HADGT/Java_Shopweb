package spring.api.management.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import spring.api.management.model.Resource.register.UpdateRequest;

public class AddressFieldsValidator implements ConstraintValidator<ValidAddressFields, UpdateRequest> {
    @Override
    public boolean isValid(UpdateRequest value, ConstraintValidatorContext context) {
        // Kiểm tra xem có bất kỳ trường ID nào có giá trị không
        boolean hasAny =
                value.getCountryID() > 0 ||
                        value.getCityID() != null ||
                        value.getDistrictID() != null ||
                        value.getLocationID() != null;

        // Kiểm tra tất cả các trường đều được điền đầy đủ
        boolean allFilled =
                value.getCountryID() > 0 &&
                        value.getCityID() != null &&
                        value.getDistrictID() != null &&
                        value.getLocationID() != null;

        // Nếu có trường nào đó được nhập nhưng không đủ 4 trường ID, thì lỗi
        return !hasAny || allFilled;
    }
}