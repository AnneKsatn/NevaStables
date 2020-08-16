package com.nevastables.repository;

import com.nevastables.domain.StandingCategogy;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StandingCategogy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StandingCategogyRepository extends JpaRepository<StandingCategogy, Long> {
}
