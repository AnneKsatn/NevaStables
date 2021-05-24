package com.nevastables.domain;

import com.nevastables.domain.enumeration.VetStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A CustomVet.
 */

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomVetToFront extends  CustomVet {

    private String horseName;
    private String hn;
//
//    public CustomVetToFront(){
//        super();
//    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomVetToFront)) {
            return false;
        }
        return id != null && id.equals(((CustomVetToFront) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomVetToFront{" +
            "id=" + id +
            ", horseId=" + horseId +
            ", status=" + status +
            ", date=" + date +
            ", title='" + title + '\'' +
            ", price=" + price +
            ", doctor='" + doctor + '\'' +
            ", note='" + note + '\'' +
            '}';
    }
}
