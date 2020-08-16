package com.nevastables.repository;

import com.nevastables.domain.Stable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Stable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StableRepository extends JpaRepository<Stable, Long> {
}
