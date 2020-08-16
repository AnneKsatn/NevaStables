package com.nevastables.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A StandingCategogy.
 */
@Entity
@Table(name = "standing_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StandingCategogy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stable_id", nullable = false)
    private Long stableId;

    @Column(name = "price")
    private Long price;

    @Column(name = "title")
    private String title;

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

    public StandingCategogy stableId(Long stableId) {
        this.stableId = stableId;
        return this;
    }

    public void setStableId(Long stableId) {
        this.stableId = stableId;
    }

    public Long getPrice() {
        return price;
    }

    public StandingCategogy price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public StandingCategogy title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StandingCategogy)) {
            return false;
        }
        return id != null && id.equals(((StandingCategogy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StandingCategogy{" +
            "id=" + getId() +
            ", stableId=" + getStableId() +
            ", price=" + getPrice() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
