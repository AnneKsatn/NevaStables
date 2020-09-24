package com.nevastables.domain;

import com.nevastables.domain.enumeration.VetStatus;

import java.io.Serializable;
import java.util.Objects;

public class StableVetToFront implements Serializable {

    public StableVetToFront(String horseName, Long id, Long horseId, Long stableVetInfoId, VetStatus status) {
        this.horseName = horseName;
        this.id = id;
        this.horseId = horseId;
        this.stableVetInfoId = stableVetInfoId;
        this.status = status;
    }

    private String horseName;

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Long getStableVetInfoId() {
        return stableVetInfoId;
    }

    public void setStableVetInfoId(Long stableVetInfoId) {
        this.stableVetInfoId = stableVetInfoId;
    }

    public VetStatus getStatus() {
        return status;
    }

    public void setStatus(VetStatus status) {
        this.status = status;
    }

    private Long id;
    private Long horseId;
    private Long stableVetInfoId;
    private VetStatus status;


    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StableVetToFront{" +
            "horseName='" + horseName + '\'' +
            ", id=" + id +
            ", horseId=" + horseId +
            ", stableVetInfoId=" + stableVetInfoId +
            ", status=" + status +
            '}';
    }
}
