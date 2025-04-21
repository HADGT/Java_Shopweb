package spring.api.management.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Sử dụng GenerationType.AUTO
    @Column(name = "city_id",updatable = false, nullable = false, unique = true)
    private UUID CityId;

    @Column(name = "city_name", nullable = false)
    private String CityName;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "country_id", referencedColumnName = "country_id", insertable = false, updatable = false),
            @JoinColumn(name = "id", referencedColumnName = "ID", insertable = false, updatable = false)
    })
    private Countries country;  // Thêm mối quan hệ với Countries

    @Column(name = "country_id", length = 2, nullable = false)
    private String CountryId;  // Giữ lại để làm khóa ngoại

    @Column(name = "id")
    private int ID;  // Giữ lại để làm khóa ngoại

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Cities cities = (Cities) o;
        return getCityId() != null && Objects.equals(getCityId(), cities.getCityId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
