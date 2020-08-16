package com.nevastables.repository;

import com.nevastables.domain.CategoryServices;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CategoryServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryServicesRepository extends JpaRepository<CategoryServices, Long> {
    List<CategoryServices> findAllByCategoryId(Long categoryId);
    Long deleteByCategoryId(Long categoryId);
}
