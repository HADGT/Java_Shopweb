package spring.api.management.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable // 👈 Khai báo đây là 1 khóa chính nhúng
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryKey implements Serializable {
    @Column(name = "id", nullable = false)
    private int ID;

    @Column(name = "country_id", length = 2, nullable = false)
    private String CountryId;

    // equals và hashCode là bắt buộc
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryKey that)) return false;
        return ID == that.ID && Objects.equals(CountryId, that.CountryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, CountryId);
    }
}
