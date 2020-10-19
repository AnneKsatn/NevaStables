package com.nevastables.web.rest;

import com.nevastables.domain.CustomVetFromFront;
import com.nevastables.domain.UserVet;
import com.nevastables.domain.UserVetInfo;
import com.nevastables.repository.UserVetInfoRepository;
import com.nevastables.repository.UserVetRepository;
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
 * REST controller for managing {@link com.nevastables.domain.UserVetInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserVetInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserVetInfoResource.class);

    private static final String ENTITY_NAME = "userVetInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserVetInfoRepository userVetInfoRepository;
    private final UserVetRepository userVetRepository;

    public UserVetInfoResource(UserVetInfoRepository userVetInfoRepository, UserVetRepository userVetRepository) {
        this.userVetInfoRepository = userVetInfoRepository;
        this.userVetRepository = userVetRepository;
    }

    /**
     * {@code POST  /user-vet-infos} : Create a new userVetInfo.
     *
     * @param customVet the userVetInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userVetInfo, or with status {@code 400 (Bad Request)} if the userVetInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-vet-infos")
    public ResponseEntity<UserVetInfo> createUserVetInfo(@Valid @RequestBody CustomVetFromFront customVet) throws URISyntaxException {

        log.debug("REST request to save UserVetInfo : {}", customVet);

        UserVetInfo userVetInfo = new UserVetInfo();
        userVetInfo.setDate(customVet.getDate());
        userVetInfo.setDoctor(customVet.getDoctor());
        userVetInfo.setTitle(customVet.getTitle());
        userVetInfo.setPrice(customVet.getPrice());


        if (userVetInfo.getId() != null) {
            throw new BadRequestAlertException("A new userVetInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserVetInfo result = userVetInfoRepository.save(userVetInfo);

        UserVet userVet = new UserVet();
        userVet.setHorseId(customVet.getHorseId());
        userVet.setStatus(customVet.getStatus());
        userVet.setUserVetInfoId(result.getId());

        UserVet userVetResult = userVetRepository.save(userVet);

        return ResponseEntity.created(new URI("/api/user-vet-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-vet-infos} : Updates an existing userVetInfo.
     *
     * @param userVetInfo the userVetInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userVetInfo,
     * or with status {@code 400 (Bad Request)} if the userVetInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userVetInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-vet-infos")
    public ResponseEntity<UserVetInfo> updateUserVetInfo(@Valid @RequestBody UserVetInfo userVetInfo) throws URISyntaxException {
        log.debug("REST request to update UserVetInfo : {}", userVetInfo);
        if (userVetInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserVetInfo result = userVetInfoRepository.save(userVetInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userVetInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-vet-infos} : get all the userVetInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userVetInfos in body.
     */
    @GetMapping("/user-vet-infos")
    public List<UserVetInfo> getAllUserVetInfos() {
        log.debug("REST request to get all UserVetInfos");
        return userVetInfoRepository.findAll();
    }

    /**
     * {@code GET  /user-vet-infos/:id} : get the "id" userVetInfo.
     *
     * @param id the id of the userVetInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userVetInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-vet-infos/{id}")
    public ResponseEntity<UserVetInfo> getUserVetInfo(@PathVariable Long id) {
        log.debug("REST request to get UserVetInfo : {}", id);
        Optional<UserVetInfo> userVetInfo = userVetInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userVetInfo);
    }

    /**
     * {@code DELETE  /user-vet-infos/:id} : delete the "id" userVetInfo.
     *
     * @param id the id of the userVetInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-vet-infos/{id}")
    public ResponseEntity<Void> deleteUserVetInfo(@PathVariable Long id) {
        log.debug("REST request to delete UserVetInfo : {}", id);

        userVetInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
