package com.nevastables.repository;

import com.nevastables.domain.Horse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Horse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorseRepository extends JpaRepository<Horse, Long> {
    List<Horse> findAllByOwnerId(Long ownerId);
}
