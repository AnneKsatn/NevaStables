package com.nevastables.web.rest;

import com.nevastables.domain.CustomVet;
import com.nevastables.domain.CustomVetToFront;
import com.nevastables.repository.CustomVetRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nevastables.domain.CustomVet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomVetResource {

    private final Logger log = LoggerFactory.getLogger(CustomVetResource.class);

    private static final String ENTITY_NAME = "customVet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomVetRepository customVetRepository;
    private final HorseRepository horseRepository;

    public CustomVetResource(CustomVetRepository customVetRepository,
                             HorseRepository horseRepository) {
        this.customVetRepository = customVetRepository;
        this.horseRepository = horseRepository;
    }

    /**
     * {@code POST  /custom-vets} : Create a new customVet.
     *
     * @param customVet the customVet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customVet, or with status {@code 400 (Bad Request)} if the customVet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-vets")
    public ResponseEntity<CustomVet> createCustomVet(@Valid @RequestBody CustomVet customVet) throws URISyntaxException {
        log.debug("REST request to save CustomVet : {}", customVet);
        if (customVet.getId() != null) {
            throw new BadRequestAlertException("A new customVet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomVet result = customVetRepository.save(customVet);
        return ResponseEntity.created(new URI("/api/custom-vets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-vets} : Updates an existing customVet.
     *
     * @param customVet the customVet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customVet,
     * or with status {@code 400 (Bad Request)} if the customVet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customVet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-vets")
    public ResponseEntity<CustomVet> updateCustomVet(@Valid @RequestBody CustomVet customVet) throws URISyntaxException {
        log.debug("REST request to update CustomVet : {}", customVet);
        if (customVet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomVet result = customVetRepository.save(customVet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customVet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /custom-vets} : get all the customVets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customVets in body.
     */
    @GetMapping("/custom-vets")
    public List<CustomVetToFront> getAllCustomVets() {
        log.debug("REST request to get all CustomVets");
        List<CustomVet> list = customVetRepository.findAll();
        List<CustomVetToFront> result = new ArrayList<>();

        for(CustomVet vet: list) {
            CustomVetToFront customVet = new CustomVetToFront();

            customVet.setTitle(vet.getTitle());
            customVet.setPrice(vet.getPrice());
            customVet.setDoctor(vet.getDoctor());
            customVet.setDate(vet.getDate());
            customVet.setHorseName(this.horseRepository.findById(vet.getHorseId()).get().getName());
            customVet.setStatus(vet.getStatus());
            customVet.setId(vet.getId());
            customVet.setNote(vet.getNote());
            customVet.setHorseId(vet.getHorseId());

            result.add(customVet);
        }
        return result;
    }

    /**
     * {@code GET  /custom-vets/:id} : get the "id" customVet.
     *
     * @param id the id of the customVet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customVet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-vets/{id}")
    public ResponseEntity<CustomVet> getCustomVet(@PathVariable Long id) {
        log.debug("REST request to get CustomVet : {}", id);
        Optional<CustomVet> customVet = customVetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customVet);
    }

    /**
     * {@code DELETE  /custom-vets/:id} : delete the "id" customVet.
     *
     * @param id the id of the customVet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-vets/{id}")
    public ResponseEntity<Void> deleteCustomVet(@PathVariable Long id) {
        log.debug("REST request to delete CustomVet : {}", id);

        customVetRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
