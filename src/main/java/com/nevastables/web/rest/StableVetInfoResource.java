package com.nevastables.web.rest;

import com.nevastables.domain.StableVetInfo;
import com.nevastables.repository.StableVetInfoRepository;
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
 * REST controller for managing {@link com.nevastables.domain.StableVetInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StableVetInfoResource {

    private final Logger log = LoggerFactory.getLogger(StableVetInfoResource.class);

    private static final String ENTITY_NAME = "stableVetInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StableVetInfoRepository stableVetInfoRepository;

    public StableVetInfoResource(StableVetInfoRepository stableVetInfoRepository) {
        this.stableVetInfoRepository = stableVetInfoRepository;
    }

    /**
     * {@code POST  /stable-vet-infos} : Create a new stableVetInfo.
     *
     * @param stableVetInfo the stableVetInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stableVetInfo, or with status {@code 400 (Bad Request)} if the stableVetInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stable-vet-infos")
    public ResponseEntity<StableVetInfo> createStableVetInfo(@Valid @RequestBody StableVetInfo stableVetInfo) throws URISyntaxException {
        log.debug("REST request to save StableVetInfo : {}", stableVetInfo);
        if (stableVetInfo.getId() != null) {
            throw new BadRequestAlertException("A new stableVetInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StableVetInfo result = stableVetInfoRepository.save(stableVetInfo);
        return ResponseEntity.created(new URI("/api/stable-vet-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stable-vet-infos} : Updates an existing stableVetInfo.
     *
     * @param stableVetInfo the stableVetInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stableVetInfo,
     * or with status {@code 400 (Bad Request)} if the stableVetInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stableVetInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stable-vet-infos")
    public ResponseEntity<StableVetInfo> updateStableVetInfo(@Valid @RequestBody StableVetInfo stableVetInfo) throws URISyntaxException {
        log.debug("REST request to update StableVetInfo : {}", stableVetInfo);
        if (stableVetInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StableVetInfo result = stableVetInfoRepository.save(stableVetInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stableVetInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stable-vet-infos} : get all the stableVetInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stableVetInfos in body.
     */
    @GetMapping("/stable-vet-infos")
    public List<StableVetInfo> getAllStableVetInfos() {
        log.debug("REST request to get all StableVetInfos");
        return stableVetInfoRepository.findAll();
    }

    /**
     * {@code GET  /stable-vet-infos/:id} : get the "id" stableVetInfo.
     *
     * @param id the id of the stableVetInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stableVetInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stable-vet-infos/{id}")
    public ResponseEntity<StableVetInfo> getStableVetInfo(@PathVariable Long id) {
        log.debug("REST request to get StableVetInfo : {}", id);
        Optional<StableVetInfo> stableVetInfo = stableVetInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stableVetInfo);
    }

    /**
     * {@code DELETE  /stable-vet-infos/:id} : delete the "id" stableVetInfo.
     *
     * @param id the id of the stableVetInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stable-vet-infos/{id}")
    public ResponseEntity<Void> deleteStableVetInfo(@PathVariable Long id) {
        log.debug("REST request to delete StableVetInfo : {}", id);

        stableVetInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
