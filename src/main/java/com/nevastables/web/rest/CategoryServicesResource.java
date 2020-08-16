package com.nevastables.web.rest;

import com.nevastables.domain.CategoryServices;
import com.nevastables.repository.CategoryServicesRepository;
import com.nevastables.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nevastables.domain.CategoryServices}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CategoryServicesResource {

    private final Logger log = LoggerFactory.getLogger(CategoryServicesResource.class);

    private static final String ENTITY_NAME = "categoryServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryServicesRepository categoryServicesRepository;

    public CategoryServicesResource(CategoryServicesRepository categoryServicesRepository) {
        this.categoryServicesRepository = categoryServicesRepository;
    }

    /**
     * {@code POST  /category-services} : Create a new categoryServices.
     *
     * @param categoryServices the categoryServices to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryServices, or with status {@code 400 (Bad Request)} if the categoryServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-services")
    public ResponseEntity<CategoryServices> createCategoryServices(@Valid @RequestBody CategoryServices categoryServices) throws URISyntaxException {
        log.debug("REST request to save CategoryServices : {}", categoryServices);
        if (categoryServices.getId() != null) {
            throw new BadRequestAlertException("A new categoryServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryServices result = categoryServicesRepository.save(categoryServices);
        return ResponseEntity.created(new URI("/api/category-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-services} : Updates an existing categoryServices.
     *
     * @param categoryServices the categoryServices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryServices,
     * or with status {@code 400 (Bad Request)} if the categoryServices is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryServices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-services")
    public ResponseEntity<CategoryServices> updateCategoryServices(@Valid @RequestBody CategoryServices categoryServices) throws URISyntaxException {
        log.debug("REST request to update CategoryServices : {}", categoryServices);
        if (categoryServices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryServices result = categoryServicesRepository.save(categoryServices);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryServices.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-services} : get all the categoryServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryServices in body.
     */
    @GetMapping("/category-services")
    public List<CategoryServices> getAllCategoryServices(@RequestParam(value="category", defaultValue = "empty") Long categoryId) {
        log.debug("REST request to get all CategoryServices");
        return categoryServicesRepository.findAllByCategoryId(categoryId);
    }

    /**
     * {@code GET  /category-services/:id} : get the "id" categoryServices.
     *
     * @param id the id of the categoryServices to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryServices, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-services/{id}")
    public ResponseEntity<CategoryServices> getCategoryServices(@PathVariable Long id) {
        log.debug("REST request to get CategoryServices : {}", id);
        Optional<CategoryServices> categoryServices = categoryServicesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categoryServices);
    }

    /**
     * {@code DELETE  /category-services/:id} : delete the "id" categoryServices.
     *
     * @param id the id of the categoryServices to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-services/{id}")
    public ResponseEntity<Void> deleteCategoryServices(@PathVariable Long id) {
        log.debug("REST request to delete CategoryServices : {}", id);

        categoryServicesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
