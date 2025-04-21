//package spring.api.management.Services;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import spring.api.management.helper.ActionMessage;
//import spring.api.management.Resource.login.LoginRequest;
//import spring.api.management.Resource.login.LoginResponse;
//import spring.api.management.helper.RestfullService;
//import spring.api.management.models.Employees;
//import spring.api.management.Resource.login.UserDto;
//import spring.api.management.Respositories.LoginRepository;
//
//import java.util.UUID;
//
//// Viết lại phương thức login theo ý muốn
//@Service
//public class loginInterf extends RestfullService implements ServiceInterf {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//    @Autowired
//    private LoginRepository loginRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    // Tạo truy vấn JPQL để đếm số lượng bản ghi trong bảng employees
//    private boolean isEmptyTable() {
//        long count = (long) entityManager.createQuery("SELECT COUNT(e.employeeId) FROM employees e").getSingleResult();  // Lấy kết quả duy nhất
//        return count == 0; // bằng 0 trả về true hoặc ngược lại
//    }
//
//    @Override
//    public LoginResponse login(LoginRequest request) {
//        try {
//            if (isEmptyTable()) {
//                logger.info("Bảng nhân viên trống, không có tài khoản nào!");
//                return new LoginResponse(null, null, ActionMessage.LOGIN_FAILED);
//            } else {
//                // Kiểm tra đăng nhập bằng email hoặc số điện thoại
//                if (request.getEmail() != null && !request.getEmail().isBlank()) {
//                    loginRepository.findByemail(request.getEmail());
//                } else if (request.getPhone() != null && !request.getPhone().isBlank()) {
//                    loginRepository.findByphoneNumber(request.getPhone());
//                }
//                Employees user = loginRepository.findByemail(request.getEmail())
//                        .orElseGet(() -> loginRepository.findByphoneNumber(request.getPhone()).orElse(null));
//
//                if (user != null && passwordEncoder.matches(request.getPassword(), user.getPass())) {
//                    String token = String.valueOf(UUID.randomUUID());
//                    UserDto userDto = new UserDto(user.getEmployeeId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
//                    return new LoginResponse(token, userDto, ActionMessage.LOGIN_SUCCESS);
//                }
//                logger.error("Sai thông tin đăng nhập cho email/phone: {}", request.getEmail() != null ? request.getEmail() : request.getPhone());
//                throw new RuntimeException(ActionMessage.LOGIN_FAILED);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(ActionMessage.UNKNOWN_ERROR);
//        }
//    }
//}
