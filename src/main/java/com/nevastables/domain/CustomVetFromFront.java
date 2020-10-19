package com.nevastables.domain;

import com.nevastables.domain.enumeration.VetStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A UserVetInfo.
 */
public class CustomVetFromFront implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Instant date;
    private String title;
    private Long price;
    private String doctor;
    private Long horseId;
    private VetStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public CustomVetFromFront date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public CustomVetFromFront title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public CustomVetFromFront price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDoctor() {
        return doctor;
    }

    public CustomVetFromFront doctor(String doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public VetStatus getStatus() {
        return status;
    }

    public void setStatus(VetStatus status) {
        this.status = status;
    }

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomVetFromFront)) {
            return false;
        }
        return id != null && id.equals(((CustomVetFromFront) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "CustomVetFromFront{" +
            "id=" + id +
            ", date=" + date +
            ", title='" + title + '\'' +
            ", price=" + price +
            ", doctor='" + doctor + '\'' +
            ", horseId=" + horseId +
            ", status=" + status +
            '}';
    }
}
