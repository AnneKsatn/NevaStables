package com.nevastables.web.rest;

import com.nevastables.domain.CustomVetFromFront;
import com.nevastables.domain.UserVet;
import com.nevastables.domain.UserVetInfo;
import com.nevastables.domain.enumeration.VetStatus;
import com.nevastables.repository.UserVetRepository;
import com.nevastables.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.graalvm.compiler.lir.LIRInstruction;
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
 * REST controller for managing {@link com.nevastables.domain.UserVet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserVetResource {

    private final Logger log = LoggerFactory.getLogger(UserVetResource.class);

    private static final String ENTITY_NAME = "userVet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserVetRepository userVetRepository;

    public UserVetResource(UserVetRepository userVetRepository) {
        this.userVetRepository = userVetRepository;
    }

    /**
     * {@code POST  /user-vets} : Create a new userVet.
     *
     * @param userVet the userVet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userVet, or with status {@code 400 (Bad Request)} if the userVet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping("/user-vets")
    public ResponseEntity<UserVet> createUserVet(@Valid @RequestBody UserVet userVet) throws URISyntaxException {
        log.debug("REST request to save UserVet : {}", userVet);

        if (userVet.getId() != null) {
            throw new BadRequestAlertException("A new userVet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserVet result = userVetRepository.save(userVet);
        return ResponseEntity.created(new URI("/api/user-vets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-vets} : Updates an existing userVet.
     *
     * @param userVet the userVet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userVet,
     * or with status {@code 400 (Bad Request)} if the userVet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userVet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-vets")
    public ResponseEntity<UserVet> updateUserVet(@Valid @RequestBody UserVet userVet) throws URISyntaxException {
        log.debug("REST request to update UserVet : {}", userVet);
        if (userVet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserVet result = userVetRepository.save(userVet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userVet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-vets} : get all the userVets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userVets in body.
     */
    @GetMapping("/user-vets")
    public List<UserVet> getAllUserVets() {
        log.debug("REST request to get all UserVets");
        return userVetRepository.findAll();
    }

    /**
     * {@code GET  /user-vets/:id} : get the "id" userVet.
     *
     * @param id the id of the userVet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userVet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-vets/{id}")
    public ResponseEntity<UserVet> getUserVet(@PathVariable Long id) {
        log.debug("REST request to get UserVet : {}", id);
        Optional<UserVet> userVet = userVetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userVet);
    }

    /**
     * {@code DELETE  /user-vets/:id} : delete the "id" userVet.
     *
     * @param id the id of the userVet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-vets/{id}")
    public ResponseEntity<Void> deleteUserVet(@PathVariable Long id) {
        log.debug("REST request to delete UserVet : {}", id);

        userVetRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
