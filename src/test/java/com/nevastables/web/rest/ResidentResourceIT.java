package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.Resident;
import com.nevastables.repository.ResidentRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResidentResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResidentResourceIT {

    private static final Long DEFAULT_STABLE_ID = 1L;
    private static final Long UPDATED_STABLE_ID = 2L;

    private static final Long DEFAULT_HORSE_ID = 1L;
    private static final Long UPDATED_HORSE_ID = 2L;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final Integer DEFAULT_STALL = 1;
    private static final Integer UPDATED_STALL = 2;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResidentMockMvc;

    private Resident resident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resident createEntity(EntityManager em) {
        Resident resident = new Resident()
            .stableId(DEFAULT_STABLE_ID)
            .horseId(DEFAULT_HORSE_ID)
            .date(DEFAULT_DATE)
            .categoryId(DEFAULT_CATEGORY_ID)
            .stall(DEFAULT_STALL);
        return resident;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resident createUpdatedEntity(EntityManager em) {
        Resident resident = new Resident()
            .stableId(UPDATED_STABLE_ID)
            .horseId(UPDATED_HORSE_ID)
            .date(UPDATED_DATE)
            .categoryId(UPDATED_CATEGORY_ID)
            .stall(UPDATED_STALL);
        return resident;
    }

    @BeforeEach
    public void initTest() {
        resident = createEntity(em);
    }

    @Test
    @Transactional
    public void createResident() throws Exception {
        int databaseSizeBeforeCreate = residentRepository.findAll().size();
        // Create the Resident
        restResidentMockMvc.perform(post("/api/residents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isCreated());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeCreate + 1);
        Resident testResident = residentList.get(residentList.size() - 1);
        assertThat(testResident.getStableId()).isEqualTo(DEFAULT_STABLE_ID);
        assertThat(testResident.getHorseId()).isEqualTo(DEFAULT_HORSE_ID);
        assertThat(testResident.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testResident.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testResident.getStall()).isEqualTo(DEFAULT_STALL);
    }

    @Test
    @Transactional
    public void createResidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = residentRepository.findAll().size();

        // Create the Resident with an existing ID
        resident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResidentMockMvc.perform(post("/api/residents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isBadRequest());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStableIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = residentRepository.findAll().size();
        // set the field null
        resident.setStableId(null);

        // Create the Resident, which fails.


        restResidentMockMvc.perform(post("/api/residents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isBadRequest());

        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHorseIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = residentRepository.findAll().size();
        // set the field null
        resident.setHorseId(null);

        // Create the Resident, which fails.


        restResidentMockMvc.perform(post("/api/residents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isBadRequest());

        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResidents() throws Exception {
        // Initialize the database
        residentRepository.saveAndFlush(resident);

        // Get all the residentList
        restResidentMockMvc.perform(get("/api/residents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resident.getId().intValue())))
            .andExpect(jsonPath("$.[*].stableId").value(hasItem(DEFAULT_STABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].horseId").value(hasItem(DEFAULT_HORSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].stall").value(hasItem(DEFAULT_STALL)));
    }
    
    @Test
    @Transactional
    public void getResident() throws Exception {
        // Initialize the database
        residentRepository.saveAndFlush(resident);

        // Get the resident
        restResidentMockMvc.perform(get("/api/residents/{id}", resident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resident.getId().intValue()))
            .andExpect(jsonPath("$.stableId").value(DEFAULT_STABLE_ID.intValue()))
            .andExpect(jsonPath("$.horseId").value(DEFAULT_HORSE_ID.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.stall").value(DEFAULT_STALL));
    }
    @Test
    @Transactional
    public void getNonExistingResident() throws Exception {
        // Get the resident
        restResidentMockMvc.perform(get("/api/residents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResident() throws Exception {
        // Initialize the database
        residentRepository.saveAndFlush(resident);

        int databaseSizeBeforeUpdate = residentRepository.findAll().size();

        // Update the resident
        Resident updatedResident = residentRepository.findById(resident.getId()).get();
        // Disconnect from session so that the updates on updatedResident are not directly saved in db
        em.detach(updatedResident);
        updatedResident
            .stableId(UPDATED_STABLE_ID)
            .horseId(UPDATED_HORSE_ID)
            .date(UPDATED_DATE)
            .categoryId(UPDATED_CATEGORY_ID)
            .stall(UPDATED_STALL);

        restResidentMockMvc.perform(put("/api/residents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedResident)))
            .andExpect(status().isOk());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
        Resident testResident = residentList.get(residentList.size() - 1);
        assertThat(testResident.getStableId()).isEqualTo(UPDATED_STABLE_ID);
        assertThat(testResident.getHorseId()).isEqualTo(UPDATED_HORSE_ID);
        assertThat(testResident.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testResident.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testResident.getStall()).isEqualTo(UPDATED_STALL);
    }

    @Test
    @Transactional
    public void updateNonExistingResident() throws Exception {
        int databaseSizeBeforeUpdate = residentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResidentMockMvc.perform(put("/api/residents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isBadRequest());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResident() throws Exception {
        // Initialize the database
        residentRepository.saveAndFlush(resident);

        int databaseSizeBeforeDelete = residentRepository.findAll().size();

        // Delete the resident
        restResidentMockMvc.perform(delete("/api/residents/{id}", resident.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
