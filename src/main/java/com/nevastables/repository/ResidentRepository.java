package com.nevastables.repository;

import com.nevastables.domain.Resident;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Resident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findAllByStableId(Long stableId);
}
