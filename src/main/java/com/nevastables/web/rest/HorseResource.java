package com.nevastables.web.rest;

import com.nevastables.domain.Horse;
import com.nevastables.repository.HorseRepository;
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
 * REST controller for managing {@link com.nevastables.domain.Horse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HorseResource {

    private final Logger log = LoggerFactory.getLogger(HorseResource.class);

    private static final String ENTITY_NAME = "horse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorseRepository horseRepository;

    public HorseResource(HorseRepository horseRepository) {
        this.horseRepository = horseRepository;
    }

    /**
     * {@code POST  /horses} : Create a new horse.
     *
     * @param horse the horse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horse, or with status {@code 400 (Bad Request)} if the horse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horses")
    public ResponseEntity<Horse> createHorse(@Valid @RequestBody Horse horse) throws URISyntaxException {
        log.debug("REST request to save Horse : {}", horse);
        if (horse.getId() != null) {
            throw new BadRequestAlertException("A new horse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Horse result = horseRepository.save(horse);
        return ResponseEntity.created(new URI("/api/horses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horses} : Updates an existing horse.
     *
     * @param horse the horse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horse,
     * or with status {@code 400 (Bad Request)} if the horse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horses")
    public ResponseEntity<Horse> updateHorse(@Valid @RequestBody Horse horse) throws URISyntaxException {
        log.debug("REST request to update Horse : {}", horse);
        if (horse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Horse result = horseRepository.save(horse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horses} : get all the horses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horses in body.
     */
    @GetMapping("/horses")
    public List<Horse> getAllHorses() {
        log.debug("REST request to get all Horses");
        return horseRepository.findAll();
    }

    @GetMapping("/user-horses")
    public List<Horse> getUserHorses(@RequestParam(value="owner", defaultValue = "empty") Long ownerId) {
        log.debug("REST request to get all Horses");
        return horseRepository.findAllByOwnerId(ownerId);
    }

    /**
     * {@code GET  /horses/:id} : get the "id" horse.
     *
     * @param id the id of the horse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horses/{id}")
    public ResponseEntity<Horse> getHorse(@PathVariable Long id) {
        log.debug("REST request to get Horse : {}", id);
        Optional<Horse> horse = horseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(horse);
    }

    /**
     * {@code DELETE  /horses/:id} : delete the "id" horse.
     *
     * @param id the id of the horse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horses/{id}")
    public ResponseEntity<Void> deleteHorse(@PathVariable Long id) {
        log.debug("REST request to delete Horse : {}", id);

        horseRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
