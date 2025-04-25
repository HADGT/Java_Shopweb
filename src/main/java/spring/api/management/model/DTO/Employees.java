package spring.api.management.model.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employees {
    @Transient
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Transient
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Sử dụng GenerationType.AUTO
    @Column(name = "employee_id", nullable = false, updatable = false, unique = true)
    private UUID EmployeeId;

    @Column(name = "first_name", nullable = false)
    private String FirstName;

    @Column(name = "last_name", nullable = false)
    private String LastName;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Định dạng email sai!")
    private String Email;

    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^(\\+84|0)[3-9][0-9]{8}$", message = "Định dạng số điện thoại sai!")
    private String PhoneNumber;

    @Column(name = "created_at", updatable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime CreatedAt;

    @Column(name = "job_id")
    private UUID JobId;

    @Column(name = "status", nullable = false)
    private boolean Status; //-- 1(true), 0(false)

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime UpdatedAt;

    @Column(name = "pass")
    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Mật khẩu phải bao gồm cả chữ và số!"
    )
    private String Pass;

    @Column(name = "token")
    private String Token;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations location;

    @Column(name = "description")
    private String Description;

    @PrePersist // annotation chỉ định dữ liệu được đưa vào trước khi vào đến cơ sở dữ liệu
    protected void onCreated() {
        logger.info("Khởi tạo với ngày = {}, lương = {}, hoa hồng = {}, trạng thái: {}", CreatedAt, Status);
        CreatedAt = LocalDateTime.now();
        Status = true;
        // Mã hóa mật khẩu trước khi lưu
        if (Pass != null) {
            Pass = passwordEncoder.encode(Pass);
        }
    }

    @PreUpdate
    protected void onUpdated() {
        logger.info("Ngày chỉnh sửa gần nhất = {}", UpdatedAt);
        UpdatedAt = LocalDateTime.now();
        // Cập nhật mật khẩu nếu thay đổi (không mã hóa lại mật khẩu cũ)
        if (Pass != null && !Pass.startsWith("$2a$")) {
            Pass = passwordEncoder.encode(Pass);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Employees employees = (Employees) o;
        return getEmployeeId() != null && Objects.equals(getEmployeeId(), employees.getEmployeeId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
