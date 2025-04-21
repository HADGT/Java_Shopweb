CREATE TABLE users (
    user_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(), -- Khóa chính, định danh duy nhất cho người dùng

    email NVARCHAR(255) NOT NULL UNIQUE,                  -- Địa chỉ email người dùng (bắt buộc, không trùng lặp)
    username NVARCHAR(255) NULL,                          -- Tên đăng nhập (không bắt buộc, có thể trống)
    password_hash NVARCHAR(255) NOT NULL,                 -- Mật khẩu đã được mã hóa (bắt buộc)

    user_type VARCHAR(50) NOT NULL,                       -- Loại người dùng: 'employee' hoặc 'customer'
    ref_id UNIQUEIDENTIFIER NOT NULL,                     -- Khóa ngoại liên kết đến bảng employee hoặc customer tùy theo user_type
    role_id UNIQUEIDENTIFIER NOT NULL,                    -- Khóa ngoại liên kết đến bảng roles (quyền của người dùng)

    email_confirmed BIT DEFAULT 0,                        -- Đánh dấu đã xác nhận email (0: chưa, 1: đã xác nhận)
    phone_number VARCHAR(20),                             -- Số điện thoại (tùy chọn)
    two_factor_enabled BIT DEFAULT 0,                     -- Bật xác thực hai yếu tố (2FA) hay không
    lockout_enabled BIT DEFAULT 0,                        -- Tài khoản có thể bị khóa hay không
    lockout_end DATETIME NULL,                            -- Thời điểm tài khoản hết bị khóa (nếu có)
    access_failed_count INT DEFAULT 0,                    -- Số lần đăng nhập thất bại liên tiếp

    refresh_token NVARCHAR(255),                          -- Token dùng để làm mới access token khi đăng nhập (tùy chọn)

    created_at DATETIME DEFAULT GETDATE(),                -- Thời điểm tạo người dùng
    updated_at DATETIME DEFAULT GETDATE(),                -- Thời điểm cập nhật cuối cùng

    CONSTRAINT FK_Users_Roles FOREIGN KEY (role_id) REFERENCES roles(role_id) -- Ràng buộc khóa ngoại tới bảng roles
)