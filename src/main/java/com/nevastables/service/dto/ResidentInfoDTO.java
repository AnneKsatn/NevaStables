package com.nevastables.service.dto;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Resident.
 */

public class ResidentInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long stableId;

    private Long horseId;

    public ResidentInfoDTO(Long id, Long stableId, Long horseId, Instant date, Long categoryId, Integer stall, String name, String categoryTitle) {
        this.id = id;
        this.stableId = stableId;
        this.horseId = horseId;
        this.date = date;
        this.categoryId = categoryId;
        this.stall = stall;
        this.name = name;
        this.categoryTitle = categoryTitle;
    }

    private Instant date;

    private Long categoryId;

    private Integer stall;

    private String name;
    private String categoryTitle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

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

    public ResidentInfoDTO stableId(Long stableId) {
        this.stableId = stableId;
        return this;
    }

    public void setStableId(Long stableId) {
        this.stableId = stableId;
    }

    public Long getHorseId() {
        return horseId;
    }

    public ResidentInfoDTO horseId(Long horseId) {
        this.horseId = horseId;
        return this;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Instant getDate() {
        return date;
    }

    public ResidentInfoDTO date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ResidentInfoDTO categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStall() {
        return stall;
    }

    public ResidentInfoDTO stall(Integer stall) {
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
        if (!(o instanceof ResidentInfoDTO)) {
            return false;
        }
        return id != null && id.equals(((ResidentInfoDTO) o).id);
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
            ", name=" + getName() +
            ", categoryTitle=" + getCategoryTitle() +
            ", stall=" + getStall() +
            "}";
    }
}
