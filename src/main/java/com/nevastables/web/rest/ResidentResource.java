package com.nevastables.web.rest;

import com.nevastables.domain.Horse;
import com.nevastables.domain.Resident;
import com.nevastables.domain.StandingCategogy;
import com.nevastables.repository.HorseRepository;
import com.nevastables.repository.ResidentRepository;
import com.nevastables.repository.StandingCategogyRepository;
import com.nevastables.service.dto.ResidentInfoDTO;
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
 * REST controller for managing {@link com.nevastables.domain.Resident}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResidentResource {

    private final Logger log = LoggerFactory.getLogger(ResidentResource.class);

    private static final String ENTITY_NAME = "resident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResidentRepository residentRepository;
    private final HorseRepository horseRepository;
    private final StandingCategogyRepository categoryRepository;

    public ResidentResource(ResidentRepository residentRepository, HorseRepository horseRepository,
                            StandingCategogyRepository categoryRepository) {
        this.residentRepository = residentRepository;
        this.horseRepository = horseRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@code POST  /residents} : Create a new resident.
     *
     * @param resident the resident to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resident, or with status {@code 400 (Bad Request)} if the resident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/residents")
    public ResponseEntity<Resident> createResident(@Valid @RequestBody Resident resident) throws URISyntaxException {
        log.debug("REST request to save Resident : {}", resident);
        if (resident.getId() != null) {
            throw new BadRequestAlertException("A new resident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resident result = residentRepository.save(resident);
        return ResponseEntity.created(new URI("/api/residents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /residents} : Updates an existing resident.
     *
     * @param resident the resident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resident,
     * or with status {@code 400 (Bad Request)} if the resident is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/residents")
    public ResponseEntity<Resident> updateResident(@Valid @RequestBody Resident resident) throws URISyntaxException {
        log.debug("REST request to update Resident : {}", resident);
        if (resident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Resident result = residentRepository.save(resident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resident.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /residents} : get all the residents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of residents in body.
     */
    @GetMapping("/residents")
    public List<ResidentInfoDTO> getAllResidents() {
        log.debug("REST request to get all Residents");

        List<Resident> residents = residentRepository.findAll();
        List<ResidentInfoDTO> residentInfos = new ArrayList<ResidentInfoDTO>();

        for (int i = 0; i < residents.size(); i++) {

            Resident resident = residents.get(i);
            Horse horse = this.horseRepository.findById(resident.getHorseId()).get();
            StandingCategogy category = this.categoryRepository.findById(resident.getCategoryId()).get();

            residentInfos.add(new ResidentInfoDTO(
                resident.getId(),
                resident.getStableId(),
                resident.getHorseId(),
                resident.getDate(),
                resident.getCategoryId(),
                resident.getStall(),
                horse.getName(),
                category.getTitle()
            ));
        }
        return residentInfos;
    }

    @GetMapping("/is_residents")
    public Boolean isResidentd(@RequestParam(value="horseId") Long horseID) {
        log.debug("REST request to get all Residents by HorseID");

        Boolean result = Boolean.TRUE;

        List<Resident> residents = residentRepository.findAllByHorseId(horseID);
        if(residents.size() == 0) {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * {@code GET  /residents/:id} : get the "id" resident.
     *
     * @param id the id of the resident to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resident, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/residents/{id}")
    public ResponseEntity<Resident> getResident(@PathVariable Long id) {
        log.debug("REST request to get Resident : {}", id);
        Optional<Resident> resident = residentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resident);
    }

    /**
     * {@code DELETE  /residents/:id} : delete the "id" resident.
     *
     * @param id the id of the resident to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/residents/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        log.debug("REST request to delete Resident : {}", id);

        residentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
