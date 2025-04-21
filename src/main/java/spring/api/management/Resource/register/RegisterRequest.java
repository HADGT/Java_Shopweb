//package spring.api.management.Resource.register;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class RegisterRequest {
//    @NotBlank(message = "Email không được để trống!")
//    @Email (message = "Định dạng email sai!")
//    private String email;
//
//    @NotBlank(message = "Mật khẩu không được để trống!")
//    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!")
//    @Pattern(
//            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
//            message = "Mật khẩu phải bao gồm cả chữ và số!"
//    )
//    private String password;
//
//    @NotBlank(message = "Số điện thoại không được để trống!")
//    @Pattern(regexp = "^(\\+84|0)[3-9][0-9]{8}$", message = "Định dạng số điện thoại sai!")
//    private String phone;
//}
