package com.nevastables.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Resident.
 */
@Entity
@Table(name = "resident")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stable_id", nullable = false)
    private Long stableId;

    @NotNull
    @Column(name = "horse_id", nullable = false)
    private Long horseId;

    @Column(name = "date")
    private Instant date;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "stall")
    private Integer stall;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStableId() {
        return stableId;
    }

    public Resident stableId(Long stableId) {
        this.stableId = stableId;
        return this;
    }

    public void setStableId(Long stableId) {
        this.stableId = stableId;
    }

    public Long getHorseId() {
        return horseId;
    }

    public Resident horseId(Long horseId) {
        this.horseId = horseId;
        return this;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Instant getDate() {
        return date;
    }

    public Resident date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Resident categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStall() {
        return stall;
    }

    public Resident stall(Integer stall) {
        this.stall = stall;
        return this;
    }

    public void setStall(Integer stall) {
        this.stall = stall;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resident)) {
            return false;
        }
        return id != null && id.equals(((Resident) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resident{" +
            "id=" + getId() +
            ", stableId=" + getStableId() +
            ", horseId=" + getHorseId() +
            ", date='" + getDate() + "'" +
            ", categoryId=" + getCategoryId() +
            ", stall=" + getStall() +
            "}";
    }
}
