package com.nevastables.web.rest;

import com.nevastables.domain.StandingCategogy;
import com.nevastables.repository.CategoryServicesRepository;
import com.nevastables.repository.StandingCategogyRepository;
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
 * REST controller for managing {@link com.nevastables.domain.StandingCategogy}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StandingCategogyResource {

    private final Logger log = LoggerFactory.getLogger(StandingCategogyResource.class);

    private static final String ENTITY_NAME = "standingCategogy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandingCategogyRepository standingCategogyRepository;
    private final CategoryServicesRepository categoryServicesRepository;

    public StandingCategogyResource(StandingCategogyRepository standingCategogyRepository,
                                    CategoryServicesRepository categoryServicesRepository
                                    ) {
        this.standingCategogyRepository = standingCategogyRepository;
        this.categoryServicesRepository = categoryServicesRepository;
    }

    /**
     * {@code POST  /standing-categogies} : Create a new standingCategogy.
     *
     * @param standingCategogy the standingCategogy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new standingCategogy, or with status {@code 400 (Bad Request)} if the standingCategogy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/standing-categogies")
    public ResponseEntity<StandingCategogy> createStandingCategogy(@Valid @RequestBody StandingCategogy standingCategogy) throws URISyntaxException {
        log.debug("REST request to save StandingCategogy : {}", standingCategogy);
        if (standingCategogy.getId() != null) {
            throw new BadRequestAlertException("A new standingCategogy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StandingCategogy result = standingCategogyRepository.save(standingCategogy);
        return ResponseEntity.created(new URI("/api/standing-categogies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /standing-categogies} : Updates an existing standingCategogy.
     *
     * @param standingCategogy the standingCategogy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standingCategogy,
     * or with status {@code 400 (Bad Request)} if the standingCategogy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the standingCategogy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/standing-categogies")
    public ResponseEntity<StandingCategogy> updateStandingCategogy(@Valid @RequestBody StandingCategogy standingCategogy) throws URISyntaxException {
        log.debug("REST request to update StandingCategogy : {}", standingCategogy);
        if (standingCategogy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StandingCategogy result = standingCategogyRepository.save(standingCategogy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standingCategogy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /standing-categogies} : get all the standingCategogies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of standingCategogies in body.
     */
    @GetMapping("/standing-categogies")
    public List<StandingCategogy> getAllStandingCategogies() {
        log.debug("REST request to get all StandingCategogies");
        return standingCategogyRepository.findAll();
    }

    /**
     * {@code GET  /standing-categogies/:id} : get the "id" standingCategogy.
     *
     * @param id the id of the standingCategogy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the standingCategogy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/standing-categogies/{id}")
    public ResponseEntity<StandingCategogy> getStandingCategogy(@PathVariable Long id) {
        log.debug("REST request to get StandingCategogy : {}", id);
        Optional<StandingCategogy> standingCategogy = standingCategogyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(standingCategogy);
    }

    /**
     * {@code DELETE  /standing-categogies/:id} : delete the "id" standingCategogy.
     *
     * @param id the id of the standingCategogy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/standing-categogies/{id}")
    public ResponseEntity<Void> deleteStandingCategogy(@PathVariable Long id) {

        log.debug("REST request to delete StandingCategogy : {}", id);

        Long deleted_amount = this.categoryServicesRepository.deleteByCategoryId(id);
        System.out.println((deleted_amount));

        standingCategogyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
