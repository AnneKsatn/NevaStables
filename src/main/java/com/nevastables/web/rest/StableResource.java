package com.nevastables.web.rest;

import com.nevastables.domain.Stable;
import com.nevastables.repository.StableRepository;
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
 * REST controller for managing {@link com.nevastables.domain.Stable}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StableResource {

    private final Logger log = LoggerFactory.getLogger(StableResource.class);

    private static final String ENTITY_NAME = "stable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StableRepository stableRepository;

    public StableResource(StableRepository stableRepository) {
        this.stableRepository = stableRepository;
    }

    /**
     * {@code POST  /stables} : Create a new stable.
     *
     * @param stable the stable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stable, or with status {@code 400 (Bad Request)} if the stable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stables")
    public ResponseEntity<Stable> createStable(@Valid @RequestBody Stable stable) throws URISyntaxException {
        log.debug("REST request to save Stable : {}", stable);
        if (stable.getId() != null) {
            throw new BadRequestAlertException("A new stable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stable result = stableRepository.save(stable);
        return ResponseEntity.created(new URI("/api/stables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stables} : Updates an existing stable.
     *
     * @param stable the stable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stable,
     * or with status {@code 400 (Bad Request)} if the stable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stables")
    public ResponseEntity<Stable> updateStable(@Valid @RequestBody Stable stable) throws URISyntaxException {
        log.debug("REST request to update Stable : {}", stable);
        if (stable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stable result = stableRepository.save(stable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stable.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stables} : get all the stables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stables in body.
     */
    @GetMapping("/stables")
    public List<Stable> getAllStables() {
        log.debug("REST request to get all Stables");
        return stableRepository.findAll();
    }

    /**
     * {@code GET  /stables/:id} : get the "id" stable.
     *
     * @param id the id of the stable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stables/{id}")
    public ResponseEntity<Stable> getStable(@PathVariable Long id) {
        log.debug("REST request to get Stable : {}", id);
        Optional<Stable> stable = stableRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stable);
    }

    /**
     * {@code DELETE  /stables/:id} : delete the "id" stable.
     *
     * @param id the id of the stable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stables/{id}")
    public ResponseEntity<Void> deleteStable(@PathVariable Long id) {
        log.debug("REST request to delete Stable : {}", id);

        stableRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
