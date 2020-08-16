package com.nevastables.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.nevastables.domain.enumeration.Gender;

/**
 * A Horse.
 */
@Entity
@Table(name = "horse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Horse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "owner_id")
    private Long ownerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birth")
    private Instant birth;

    @Column(name = "color")
    private Long colorId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Horse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Horse ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Gender getGender() {
        return gender;
    }

    public Horse gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Instant getBirth() {
        return birth;
    }

    public Horse birth(Instant birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }

    public Long getColorId() {
        return colorId;
    }

    public Horse colorId(Long colorId) {
        this.colorId = colorId;
        return this;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Horse)) {
            return false;
        }
        return id != null && id.equals(((Horse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Horse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ownerId=" + getOwnerId() +
            ", gender='" + getGender() + "'" +
            ", birth='" + getBirth() + "'" +
            ", colorId=" + getColorId() +
            "}";
    }
}
