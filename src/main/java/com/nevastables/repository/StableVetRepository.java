package com.nevastables.repository;

import com.nevastables.domain.Resident;
import com.nevastables.domain.StableVet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the StableVet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StableVetRepository extends JpaRepository<StableVet, Long> {

    List<StableVet> findAllByStableVetInfoId(Long stableVetInfoId);
}
