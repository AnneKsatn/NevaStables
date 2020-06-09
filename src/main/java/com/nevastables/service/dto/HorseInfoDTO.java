package com.nevastables.service.dto;

import com.nevastables.domain.enumeration.Gender;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Horse.
 */

public class HorseInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    public String getClubTitle() {
        return clubTitle;
    }

    public void setClubTitle(String clubTitle) {
        this.clubTitle = clubTitle;
    }

    private String clubTitle;

    private Long ownerId;

    private Gender gender;

    public HorseInfoDTO(Long id, String name,
                        Long ownerId, Gender gender,
                        Instant birth, Long color,
                        String isResident, Integer clubId, String clubTitle) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.gender = gender;
        this.birth = birth;
        this.color = color;
        this.isResident = isResident;
        this.clubId = clubId;
        this.clubTitle = clubTitle;
    }

    private Instant birth;

    private Long color;

    public String getResident() {
        return isResident;
    }

    public void setResident(String resident) {
        isResident = resident;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    private String isResident;

    private Integer clubId;

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

    public HorseInfoDTO name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public HorseInfoDTO ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerID(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Gender getGender() {
        return gender;
    }

    public HorseInfoDTO gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Instant getBirth() {
        return birth;
    }

    public HorseInfoDTO birth(Instant birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }

    public Long getColor() {
        return color;
    }

    public HorseInfoDTO color(Long color) {
        this.color = color;
        return this;
    }

    public void setColor(Long color) {
        this.color = color;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorseInfoDTO)) {
            return false;
        }
        return id != null && id.equals(((HorseInfoDTO) o).id);
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
            ", color=" + getColor() + "'" +
            ", resident=" + getResident() + "'" +
            ", title=" + getClubTitle() + "'" +
            ", club_id=" + getClubId() +
            "}";
    }
}
