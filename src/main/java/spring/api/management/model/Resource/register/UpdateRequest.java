package spring.api.management.model.Resource.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.api.management.model.validation.ValidAddressFields;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidAddressFields
public class UpdateRequest {
    private String FirstName;
    private String LastName;
    @Email(message = "Định dạng email sai!")
    private String Email;
    @Pattern(regexp = "^(\\+84|0)[3-9][0-9]{8}$", message = "Định dạng số điện thoại sai!")
    private String PhoneNumber;
    private UUID JobId;
    private String Description;
    private int CountryID;
    private UUID CityID;
    private UUID DistrictID;
    private UUID LocationID;
}
