package com.nevastables.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A StableVetInfo.
 */
@Entity
@Table(name = "stable_vet_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StableVetInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stable_id", nullable = false)
    private Long stableId;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

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

    public StableVetInfo stableId(Long stableId) {
        this.stableId = stableId;
        return this;
    }

    public void setStableId(Long stableId) {
        this.stableId = stableId;
    }

    public Instant getDate() {
        return date;
    }

    public StableVetInfo date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public StableVetInfo title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public StableVetInfo price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StableVetInfo)) {
            return false;
        }
        return id != null && id.equals(((StableVetInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StableVetInfo{" +
            "id=" + getId() +
            ", stableId=" + getStableId() +
            ", date='" + getDate() + "'" +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
