package com.nevastables.repository;

import com.nevastables.domain.CustomVet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomVet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomVetRepository extends JpaRepository<CustomVet, Long> {
}
