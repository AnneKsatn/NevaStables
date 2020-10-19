package com.nevastables.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.nevastables.domain.enumeration.VetStatus;

/**
 * A UserVet.
 */
@Entity
@Table(name = "user_vet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserVet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "horse_id", nullable = false)
    private Long horseId;

    @NotNull
    @Column(name = "user_vet_info_id", nullable = false)
    private Long userVetInfoId;

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

    public UserVet horseId(Long horseId) {
        this.horseId = horseId;
        return this;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Long getUserVetInfoId() {
        return userVetInfoId;
    }

    public UserVet userVetInfoId(Long userVetInfoId) {
        this.userVetInfoId = userVetInfoId;
        return this;
    }

    public void setUserVetInfoId(Long userVetInfoId) {
        this.userVetInfoId = userVetInfoId;
    }

    public VetStatus getStatus() {
        return status;
    }

    public UserVet status(VetStatus status) {
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
        if (!(o instanceof UserVet)) {
            return false;
        }
        return id != null && id.equals(((UserVet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserVet{" +
            "id=" + getId() +
            ", horseId=" + getHorseId() +
            ", userVetInfoId=" + getUserVetInfoId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
