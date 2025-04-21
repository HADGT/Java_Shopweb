package spring.api.management.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobs")
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Sử dụng GenerationType.AUTO
    @Column(name = "job_id", nullable = false, updatable = false, unique = true)
    private UUID JobId;

    @Column(name = "job_title", nullable = false, length = 50)
    private String JobTitle;

    @Column(name = "min_salary", precision = 10)
    private BigDecimal MinSalary;

    @Column(name = "max_salary", precision = 10)
    private BigDecimal MaxSalary;

    @PrePersist
    protected void onCreated() {
        if(MaxSalary == null){MaxSalary = BigDecimal.valueOf(0);}
        if(MinSalary == null){MinSalary = BigDecimal.valueOf(0);}
    }

    // Kiểm tra điều kiện trước khi lưu hoặc cập nhật
    @PreUpdate
    protected void validateSalary() {
        if (MinSalary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Min salary không thể âm!");
        }
        if (MaxSalary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Max salary không thể âm!");
        }
        if (MinSalary.compareTo(MaxSalary) > 0) {
            throw new IllegalArgumentException("Min salary không thể lớn hơn max salary!");
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Jobs jobs = (Jobs) o;
        return getJobId() != null && Objects.equals(getJobId(), jobs.getJobId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
