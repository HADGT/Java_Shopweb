package spring.api.management.model.Resource.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "FirstName không được để trống!")
    private String FirstName;
    @NotBlank(message = "LastName không được để trống!")
    private String LastName;
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Định dạng email sai!")
    private String Email;
    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^(\\+84|0)[3-9][0-9]{8}$", message = "Định dạng số điện thoại sai!")
    private String PhoneNumber;
    private UUID JobId;
    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Mật khẩu phải bao gồm cả chữ và số!"
    )
    private String Pass;

    @NotBlank(message = "Tên quốc gia không được để trống!")
    private int CountryID;

    @NotBlank(message = "Tên thành phố không được để trống!")
    private UUID CityID;

    @NotBlank(message = "Tên quận/huyện không được để trống!")
    private UUID DistrictID;

    @NotBlank(message = "Địa chỉ cụ thể không được để trống!")
    private UUID LocationID;
}
