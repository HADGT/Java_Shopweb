//package spring.api.management.Resource.login;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//
////kho dữ liệu yêu cầu đăng nhập
//@Getter
//@Setter
//public class LoginRequest {
//    @Email(message = "Định dạng email sai!")
//    private String Email;
//
//    @Pattern(regexp = "^(\\+84|0)[3-9][0-9]{8}$", message = "Định dạng số điện thoại sai!")
//    private String Phone;
//
//    @NotBlank(message = "Mật khẩu không được để trống!")
//    private String Password;
//}
