package com.nevastables.web.rest;

import com.nevastables.domain.StableVet;
import com.nevastables.repository.StableVetRepository;
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
 * REST controller for managing {@link com.nevastables.domain.StableVet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StableVetResource {

    private final Logger log = LoggerFactory.getLogger(StableVetResource.class);

    private static final String ENTITY_NAME = "stableVet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StableVetRepository stableVetRepository;

    public StableVetResource(StableVetRepository stableVetRepository) {
        this.stableVetRepository = stableVetRepository;
    }

    /**
     * {@code POST  /stable-vets} : Create a new stableVet.
     *
     * @param stableVet the stableVet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stableVet, or with status {@code 400 (Bad Request)} if the stableVet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stable-vets")
    public ResponseEntity<StableVet> createStableVet(@Valid @RequestBody StableVet stableVet) throws URISyntaxException {
        log.debug("REST request to save StableVet : {}", stableVet);
        if (stableVet.getId() != null) {
            throw new BadRequestAlertException("A new stableVet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StableVet result = stableVetRepository.save(stableVet);
        return ResponseEntity.created(new URI("/api/stable-vets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stable-vets} : Updates an existing stableVet.
     *
     * @param stableVet the stableVet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stableVet,
     * or with status {@code 400 (Bad Request)} if the stableVet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stableVet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stable-vets")
    public ResponseEntity<StableVet> updateStableVet(@Valid @RequestBody StableVet stableVet) throws URISyntaxException {
        log.debug("REST request to update StableVet : {}", stableVet);
        if (stableVet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StableVet result = stableVetRepository.save(stableVet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stableVet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stable-vets} : get all the stableVets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stableVets in body.
     */
    @GetMapping("/stable-vets")
    public List<StableVet> getAllStableVets() {
        log.debug("REST request to get all StableVets");
        return stableVetRepository.findAll();
    }

    /**
     * {@code GET  /stable-vets/:id} : get the "id" stableVet.
     *
     * @param id the id of the stableVet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stableVet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stable-vets/{id}")
    public ResponseEntity<StableVet> getStableVet(@PathVariable Long id) {
        log.debug("REST request to get StableVet : {}", id);
        Optional<StableVet> stableVet = stableVetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stableVet);
    }

    /**
     * {@code DELETE  /stable-vets/:id} : delete the "id" stableVet.
     *
     * @param id the id of the stableVet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stable-vets/{id}")
    public ResponseEntity<Void> deleteStableVet(@PathVariable Long id) {
        log.debug("REST request to delete StableVet : {}", id);

        stableVetRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
