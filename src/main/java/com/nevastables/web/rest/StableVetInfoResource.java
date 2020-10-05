package com.nevastables.web.rest;

import com.nevastables.domain.*;
import com.nevastables.domain.enumeration.VetStatus;
import com.nevastables.repository.*;
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
    private final ResidentRepository residentRepository;
    private final StableVetRepository stableVetRepository;
    private final HorseRepository horseRepository;
    private final StableRepository stableRepository;

    public StableVetInfoResource(StableVetInfoRepository stableVetInfoRepository,
                                 ResidentRepository residentRepository,
                                 StableVetRepository stableVetRepository,
                                 HorseRepository horseRepository,
                                 StableRepository stableRepository
                                 ) {
        this.stableVetInfoRepository = stableVetInfoRepository;
        this.residentRepository = residentRepository;
        this.stableVetRepository = stableVetRepository;
        this.horseRepository = horseRepository;
        this.stableRepository = stableRepository;
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
        System.out.println(stableVetInfo);

        Long stableId = stableVetInfo.getStableId();
        List<Resident> residents = this.residentRepository.findAllByStableId(stableId);
        List<StableVet> record = new ArrayList<StableVet>();

        log.debug("REST request to save StableVetInfo : {}", stableVetInfo);
        if (stableVetInfo.getId() != null) {
            throw new BadRequestAlertException("A new stableVetInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }

        StableVetInfo result = stableVetInfoRepository.save(stableVetInfo);

        for (Resident resident: residents) {
            StableVet stableVet = new StableVet();

            stableVet.setHorseId(resident.getHorseId());
            stableVet.setStatus(VetStatus.NOTPAID);
            stableVet.setStableVetInfoId(result.getId());

            record.add(stableVet);
        }

        stableVetRepository.saveAll(record);

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


    @GetMapping("/stable-vet-infos-for-user")
    public List<StableVetToUser> getAllStableVetInfosByUser() {
        log.debug("REST request to get all StableVetInfos");
        int uid = 1;

        List<Horse> horses = this.horseRepository.findAllByOwnerId(Long.valueOf(uid));
        List<StableVetToUser> vets = new ArrayList<>();

        for(Horse horse: horses) {
//            System.out.println(horse.toString());

            List<StableVet> horseVets = this.stableVetRepository.findAllByHorseId(horse.getId());
            for(StableVet horseVet: horseVets) {
                System.out.println(horseVet.toString());

                Long stableVetInfoId = horseVet.getStableVetInfoId();

                Optional<StableVetInfo> vetInfo = this.stableVetInfoRepository.findById(stableVetInfoId);
                Optional<Stable> stable = this.stableRepository.findById(vetInfo.get().getStableId());
//
                System.out.println(vetInfo.toString());
                System.out.println(stable.toString());
                StableVetToUser vet = new StableVetToUser(
                    vetInfo.get().getDate(),
                    vetInfo.get().getTitle(),
                    horseVet.getStatus(),
                    vetInfo.get().getPrice(),
                    vetInfo.get().getId(),
                    vetInfo.get().getStableId(),
                    stable.get().getTitle(),
                    horse.getId(),
                    horseVet.getId(),
                    horse.getName()
                    );

                vets.add(vet);
            }
        }

        return vets;
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
