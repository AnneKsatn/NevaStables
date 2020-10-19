package com.nevastables.repository;

import com.nevastables.domain.UserVet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserVet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserVetRepository extends JpaRepository<UserVet, Long> {
}
