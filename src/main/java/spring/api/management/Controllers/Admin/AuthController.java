//package spring.api.management.Controllers;
//
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import spring.api.management.Services.ServiceInterf;
//
//// đăng nhập
//@Validated
//@RestController
//@RequestMapping("api/v1/auth")
//public class AuthController {
//    private final ServiceInterf interf;
//
//    public AuthController(ServiceInterf interf) {
//        this.interf = interf;
//    }
//
////    @PostMapping("login")
////    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
////        if (!request.isValidLogin()) {
////            return ResponseEntity.badRequest().body(new LoginResponse(null, null, "Bạn phải nhập email hoặc số điện thoại!"));
////        }
////        LoginResponse auth = interf.login(request);
////        if (auth != null && auth.getToken() != null) {
////            return ResponseEntity.ok(new LoginResponse(auth.getToken(), auth.getUserDto(), ActionMessage.LOGIN_SUCCESS));
////        }
////        return ResponseEntity.status(401).body(new LoginResponse(null, null, ActionMessage.LOGIN_FAILED));
////    }
//}
