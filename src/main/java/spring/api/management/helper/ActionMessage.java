package spring.api.management.helper;

public class ActionMessage {

    // Hành động CRUD (Create, Read, Update, Delete)
    //Create
    public static final String CREATE_SUCCESS = "Tạo mới thành công";
    public static final String CREATE_FAILED = "Tạo mới thất bại";
    //Update
    public static final String UPDATE_SUCCESS = "Cập nhật thành công";
    public static final String UPDATE_FAILED = "Cập nhật thất bại";
    //Delete
    public static final String DELETE_SUCCESS = "Xóa thành công";
    public static final String DELETE_FAILED = "Xóa thất bại";
    //Get - Read
    public static final String RETRIEVE_SUCCESS = "Lấy dữ liệu thành công";
    public static final String RETRIEVE_FAILED = "Lấy dữ liệu thất bại";

    // Xử lý xác thực (Authentication)
    public static final String LOGIN_SUCCESS = "Đăng nhập thành công";
    public static final String LOGIN_FAILED = "Đăng nhập thất bại, tài khoản hoặc mật khẩu không đúng";
    public static final String LOGOUT_SUCCESS = "Đăng xuất thành công";

    // Xử lý quyền hạn (Authorization)
    public static final String ACCESS_DENIED = "Bạn không có quyền truy cập vào tài nguyên này";
    public static final String TOKEN_INVALID = "Token không hợp lệ hoặc đã hết hạn";
    public static final String TOKEN_REQUIRED = "Token là bắt buộc";
    public static final String SESSION_EXPIRED = "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại";

    // Xử lý tài khoản người dùng
    public static final String USER_NOT_FOUND = "Không tìm thấy người dùng";
    public static final String USER_ALREADY_EXISTS = "Người dùng đã tồn tại";
    public static final String EMAIL_ALREADY_EXISTS = "Email đã được đăng ký";
    public static final String PASSWORD_INCORRECT = "Mật khẩu không chính xác";
    public static final String PASSWORD_CHANGED_SUCCESS = "Thay đổi mật khẩu thành công";
    public static final String PASSWORD_CHANGED_FAILED = "Thay đổi mật khẩu thất bại";
    public static final String ACCOUNT_LOCKED = "Tài khoản của bạn đã bị khóa, vui lòng liên hệ quản trị viên";

    // Xử lý giao dịch & thanh toán
    public static final String PAYMENT_SUCCESS = "Thanh toán thành công";
    public static final String PAYMENT_FAILED = "Thanh toán thất bại";
    public static final String TRANSACTION_COMPLETED = "Giao dịch hoàn tất";
    public static final String TRANSACTION_FAILED = "Giao dịch thất bại";
    public static final String INSUFFICIENT_BALANCE = "Số dư tài khoản không đủ";
    public static final String PAYMENT_METHOD_INVALID = "Phương thức thanh toán không hợp lệ";
    public static final String INVOICE_GENERATED = "Hóa đơn đã được tạo thành công";
    public static final String INVOICE_FAILED = "Không thể tạo hóa đơn";

    // Hệ thống & thông báo lỗi
    public static final String SYSTEM_ERROR = "Lỗi hệ thống, vui lòng thử lại sau";
    public static final String DATABASE_ERROR = "Lỗi cơ sở dữ liệu";
    public static final String NETWORK_ERROR = "Lỗi kết nối mạng";
    public static final String INVALID_REQUEST = "Yêu cầu không hợp lệ";
    public static final String UNKNOWN_ERROR = "Lỗi không xác định";

    // Xử lý API & dữ liệu đầu vào
    public static final String API_REQUEST_SUCCESS = "Yêu cầu API thành công";
    public static final String API_REQUEST_FAILED = "Yêu cầu API thất bại";
    public static final String INVALID_PARAMETERS = "Tham số không hợp lệ";
    public static final String MISSING_REQUIRED_FIELDS = "Thiếu trường bắt buộc";
    public static final String DATA_FORMAT_INVALID = "Định dạng dữ liệu không hợp lệ";
    public static final String RATE_LIMIT_EXCEEDED = "Bạn đã gửi quá nhiều yêu cầu trong thời gian ngắn";

    // Xử lý phân quyền
    public static final String ROLE_ASSIGNED_SUCCESS = "Gán quyền thành công";
    public static final String ROLE_ASSIGNED_FAILED = "Gán quyền thất bại";

    //Tìm kiếm
    public static final String SEARCH_NULL = "Thông tin tìm kiếm đang được để trống!";
    public static final String SEARCH_FAIL = "Thông tin tìm kiếm không tồn tại!";
}
