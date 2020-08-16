package com.nevastables.repository;

import com.nevastables.domain.StableVetInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StableVetInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StableVetInfoRepository extends JpaRepository<StableVetInfo, Long> {
}
