package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.StableVet;
import com.nevastables.repository.StableVetRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nevastables.domain.enumeration.VetStatus;
/**
 * Integration tests for the {@link StableVetResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StableVetResourceIT {

    private static final Long DEFAULT_HORSE_ID = 1L;
    private static final Long UPDATED_HORSE_ID = 2L;

    private static final Long DEFAULT_STABLE_VET_INFO_ID = 1L;
    private static final Long UPDATED_STABLE_VET_INFO_ID = 2L;

    private static final VetStatus DEFAULT_STATUS = VetStatus.MISSED;
    private static final VetStatus UPDATED_STATUS = VetStatus.PAID;

    @Autowired
    private StableVetRepository stableVetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStableVetMockMvc;

    private StableVet stableVet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StableVet createEntity(EntityManager em) {
        StableVet stableVet = new StableVet()
            .horseId(DEFAULT_HORSE_ID)
            .stableVetInfoId(DEFAULT_STABLE_VET_INFO_ID)
            .status(DEFAULT_STATUS);
        return stableVet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StableVet createUpdatedEntity(EntityManager em) {
        StableVet stableVet = new StableVet()
            .horseId(UPDATED_HORSE_ID)
            .stableVetInfoId(UPDATED_STABLE_VET_INFO_ID)
            .status(UPDATED_STATUS);
        return stableVet;
    }

    @BeforeEach
    public void initTest() {
        stableVet = createEntity(em);
    }

    @Test
    @Transactional
    public void createStableVet() throws Exception {
        int databaseSizeBeforeCreate = stableVetRepository.findAll().size();
        // Create the StableVet
        restStableVetMockMvc.perform(post("/api/stable-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVet)))
            .andExpect(status().isCreated());

        // Validate the StableVet in the database
        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeCreate + 1);
        StableVet testStableVet = stableVetList.get(stableVetList.size() - 1);
        assertThat(testStableVet.getHorseId()).isEqualTo(DEFAULT_HORSE_ID);
        assertThat(testStableVet.getStableVetInfoId()).isEqualTo(DEFAULT_STABLE_VET_INFO_ID);
        assertThat(testStableVet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createStableVetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stableVetRepository.findAll().size();

        // Create the StableVet with an existing ID
        stableVet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStableVetMockMvc.perform(post("/api/stable-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVet)))
            .andExpect(status().isBadRequest());

        // Validate the StableVet in the database
        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHorseIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableVetRepository.findAll().size();
        // set the field null
        stableVet.setHorseId(null);

        // Create the StableVet, which fails.


        restStableVetMockMvc.perform(post("/api/stable-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVet)))
            .andExpect(status().isBadRequest());

        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStableVetInfoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableVetRepository.findAll().size();
        // set the field null
        stableVet.setStableVetInfoId(null);

        // Create the StableVet, which fails.


        restStableVetMockMvc.perform(post("/api/stable-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVet)))
            .andExpect(status().isBadRequest());

        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableVetRepository.findAll().size();
        // set the field null
        stableVet.setStatus(null);

        // Create the StableVet, which fails.


        restStableVetMockMvc.perform(post("/api/stable-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVet)))
            .andExpect(status().isBadRequest());

        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStableVets() throws Exception {
        // Initialize the database
        stableVetRepository.saveAndFlush(stableVet);

        // Get all the stableVetList
        restStableVetMockMvc.perform(get("/api/stable-vets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stableVet.getId().intValue())))
            .andExpect(jsonPath("$.[*].horseId").value(hasItem(DEFAULT_HORSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].stableVetInfoId").value(hasItem(DEFAULT_STABLE_VET_INFO_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getStableVet() throws Exception {
        // Initialize the database
        stableVetRepository.saveAndFlush(stableVet);

        // Get the stableVet
        restStableVetMockMvc.perform(get("/api/stable-vets/{id}", stableVet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stableVet.getId().intValue()))
            .andExpect(jsonPath("$.horseId").value(DEFAULT_HORSE_ID.intValue()))
            .andExpect(jsonPath("$.stableVetInfoId").value(DEFAULT_STABLE_VET_INFO_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStableVet() throws Exception {
        // Get the stableVet
        restStableVetMockMvc.perform(get("/api/stable-vets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStableVet() throws Exception {
        // Initialize the database
        stableVetRepository.saveAndFlush(stableVet);

        int databaseSizeBeforeUpdate = stableVetRepository.findAll().size();

        // Update the stableVet
        StableVet updatedStableVet = stableVetRepository.findById(stableVet.getId()).get();
        // Disconnect from session so that the updates on updatedStableVet are not directly saved in db
        em.detach(updatedStableVet);
        updatedStableVet
            .horseId(UPDATED_HORSE_ID)
            .stableVetInfoId(UPDATED_STABLE_VET_INFO_ID)
            .status(UPDATED_STATUS);

        restStableVetMockMvc.perform(put("/api/stable-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStableVet)))
            .andExpect(status().isOk());

        // Validate the StableVet in the database
        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeUpdate);
        StableVet testStableVet = stableVetList.get(stableVetList.size() - 1);
        assertThat(testStableVet.getHorseId()).isEqualTo(UPDATED_HORSE_ID);
        assertThat(testStableVet.getStableVetInfoId()).isEqualTo(UPDATED_STABLE_VET_INFO_ID);
        assertThat(testStableVet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingStableVet() throws Exception {
        int databaseSizeBeforeUpdate = stableVetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStableVetMockMvc.perform(put("/api/stable-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVet)))
            .andExpect(status().isBadRequest());

        // Validate the StableVet in the database
        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStableVet() throws Exception {
        // Initialize the database
        stableVetRepository.saveAndFlush(stableVet);

        int databaseSizeBeforeDelete = stableVetRepository.findAll().size();

        // Delete the stableVet
        restStableVetMockMvc.perform(delete("/api/stable-vets/{id}", stableVet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StableVet> stableVetList = stableVetRepository.findAll();
        assertThat(stableVetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
