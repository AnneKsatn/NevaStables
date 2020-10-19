package com.nevastables.repository;

import com.nevastables.domain.UserVetInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserVetInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserVetInfoRepository extends JpaRepository<UserVetInfo, Long> {
}
