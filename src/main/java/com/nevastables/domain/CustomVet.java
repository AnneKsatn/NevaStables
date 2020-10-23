package com.nevastables.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.nevastables.domain.enumeration.VetStatus;

/**
 * A CustomVet.
 */
@Entity
@Table(name = "custom_vet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomVet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @NotNull
    @Column(name = "horse_id", nullable = false)
    public Long horseId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public VetStatus status;

    @NotNull
    @Column(name = "date", nullable = false)
    public Instant date;

    @NotNull
    @Column(name = "title", nullable = false)
    public String title;

    @Column(name = "price")
    public Long price;

    @Column(name = "doctor")
    public String doctor;

    @Column(name = "note")
    public String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHorseId() {
        return horseId;
    }

    public CustomVet horseId(Long horseId) {
        this.horseId = horseId;
        return this;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public VetStatus getStatus() {
        return status;
    }

    public CustomVet status(VetStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(VetStatus status) {
        this.status = status;
    }

    public Instant getDate() {
        return date;
    }

    public CustomVet date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public CustomVet title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public CustomVet price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDoctor() {
        return doctor;
    }

    public CustomVet doctor(String doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getNote() {
        return note;
    }

    public CustomVet note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomVet)) {
            return false;
        }
        return id != null && id.equals(((CustomVet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomVet{" +
            "id=" + getId() +
            ", horseId=" + getHorseId() +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", doctor='" + getDoctor() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
