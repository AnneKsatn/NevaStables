package com.nevastables.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.nevastables.domain.enumeration.VetStatus;

/**
 * A StableVet.
 */
@Entity
@Table(name = "stable_vet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StableVet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "horse_id", nullable = false)
    private Long horseId;

    @NotNull
    @Column(name = "stable_vet_info_id", nullable = false)
    private Long stableVetInfoId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VetStatus status;

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

    public StableVet horseId(Long horseId) {
        this.horseId = horseId;
        return this;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Long getStableVetInfoId() {
        return stableVetInfoId;
    }

    public StableVet stableVetInfoId(Long stableVetInfoId) {
        this.stableVetInfoId = stableVetInfoId;
        return this;
    }

    public void setStableVetInfoId(Long stableVetInfoId) {
        this.stableVetInfoId = stableVetInfoId;
    }

    public VetStatus getStatus() {
        return status;
    }

    public StableVet status(VetStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(VetStatus status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StableVet)) {
            return false;
        }
        return id != null && id.equals(((StableVet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StableVet{" +
            "id=" + getId() +
            ", horseId=" + getHorseId() +
            ", stableVetInfoId=" + getStableVetInfoId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
