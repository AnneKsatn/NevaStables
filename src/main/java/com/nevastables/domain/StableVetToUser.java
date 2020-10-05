package com.nevastables.domain;

import com.nevastables.domain.enumeration.VetStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class StableVetToUser implements Serializable {

    public StableVetToUser(Instant date, String title, VetStatus status, Long price, Long vetId, Long clubId, String clubTitle, Long horseId, Long id, String horseName) {
        this.date = date;
        this.title = title;
        this.status = status;
        this.price = price;
        this.vetId = vetId;
        this.clubId = clubId;
        this.clubTitle = clubTitle;
        this.horseId = horseId;
        this.id = id;
        this.horseName = horseName;
    }

    private Instant date;
    private String title;
    private VetStatus status;
    private Long price;
    private Long vetId;
    private Long clubId;
    private String clubTitle;
    private Long horseId;
    private Long id;
    private String horseName;


    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VetStatus getStatus() {
        return status;
    }

    public void setStatus(VetStatus status) {
        this.status = status;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getClubTitle() {
        return clubTitle;
    }

    public void setClubTitle(String clubTitle) {
        this.clubTitle = clubTitle;
    }

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    @Override
    public String toString() {
        return "StableVetToUser{" +
            "date=" + date +
            ", title='" + title + '\'' +
            ", status=" + status +
            ", price=" + price +
            ", vetId=" + vetId +
            ", clubId=" + clubId +
            ", clubTitle='" + clubTitle + '\'' +
            ", horseId=" + horseId +
            ", id=" + id +
            ", horseName='" + horseName + '\'' +
            '}';
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
