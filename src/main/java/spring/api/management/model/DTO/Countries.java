package spring.api.management.model.DTO;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "countries")
public class Countries {
    @EmbeddedId
    private CountryKey countryKey;

    @Column(name = "country_name", nullable = false)
    private String CountryName;

    @PrePersist
    @PreUpdate
    private void formatIdToUpperCase() {
        if (countryKey != null && countryKey.getCountryId() != null) {
            countryKey.setCountryId(countryKey.getCountryId().toUpperCase());
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Countries countries = (Countries) o;
        return getCountryKey() != null && Objects.equals(getCountryKey(), countries.getCountryKey());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(countryKey);
    }
}