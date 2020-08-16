package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.Horse;
import com.nevastables.repository.HorseRepository;

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

import com.nevastables.domain.enumeration.Gender;
/**
 * Integration tests for the {@link HorseResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HorseResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final Gender DEFAULT_GENDER = Gender.MARE;
    private static final Gender UPDATED_GENDER = Gender.STALLION;

    private static final Instant DEFAULT_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_COLOR_ID = 1L;
    private static final Long UPDATED_COLOR_ID = 2L;

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHorseMockMvc;

    private Horse horse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horse createEntity(EntityManager em) {
        Horse horse = new Horse()
            .name(DEFAULT_NAME)
            .ownerId(DEFAULT_OWNER_ID)
            .gender(DEFAULT_GENDER)
            .birth(DEFAULT_BIRTH)
            .colorId(DEFAULT_COLOR_ID);
        return horse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horse createUpdatedEntity(EntityManager em) {
        Horse horse = new Horse()
            .name(UPDATED_NAME)
            .ownerId(UPDATED_OWNER_ID)
            .gender(UPDATED_GENDER)
            .birth(UPDATED_BIRTH)
            .colorId(UPDATED_COLOR_ID);
        return horse;
    }

    @BeforeEach
    public void initTest() {
        horse = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorse() throws Exception {
        int databaseSizeBeforeCreate = horseRepository.findAll().size();
        // Create the Horse
        restHorseMockMvc.perform(post("/api/horses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isCreated());

        // Validate the Horse in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeCreate + 1);
        Horse testHorse = horseList.get(horseList.size() - 1);
        assertThat(testHorse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHorse.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testHorse.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testHorse.getBirth()).isEqualTo(DEFAULT_BIRTH);
        assertThat(testHorse.getColorId()).isEqualTo(DEFAULT_COLOR_ID);
    }

    @Test
    @Transactional
    public void createHorseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horseRepository.findAll().size();

        // Create the Horse with an existing ID
        horse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorseMockMvc.perform(post("/api/horses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isBadRequest());

        // Validate the Horse in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = horseRepository.findAll().size();
        // set the field null
        horse.setName(null);

        // Create the Horse, which fails.


        restHorseMockMvc.perform(post("/api/horses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isBadRequest());

        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = horseRepository.findAll().size();
        // set the field null
        horse.setGender(null);

        // Create the Horse, which fails.


        restHorseMockMvc.perform(post("/api/horses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isBadRequest());

        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorses() throws Exception {
        // Initialize the database
        horseRepository.saveAndFlush(horse);

        // Get all the horseList
        restHorseMockMvc.perform(get("/api/horses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birth").value(hasItem(DEFAULT_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].colorId").value(hasItem(DEFAULT_COLOR_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getHorse() throws Exception {
        // Initialize the database
        horseRepository.saveAndFlush(horse);

        // Get the horse
        restHorseMockMvc.perform(get("/api/horses/{id}", horse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birth").value(DEFAULT_BIRTH.toString()))
            .andExpect(jsonPath("$.colorId").value(DEFAULT_COLOR_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingHorse() throws Exception {
        // Get the horse
        restHorseMockMvc.perform(get("/api/horses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorse() throws Exception {
        // Initialize the database
        horseRepository.saveAndFlush(horse);

        int databaseSizeBeforeUpdate = horseRepository.findAll().size();

        // Update the horse
        Horse updatedHorse = horseRepository.findById(horse.getId()).get();
        // Disconnect from session so that the updates on updatedHorse are not directly saved in db
        em.detach(updatedHorse);
        updatedHorse
            .name(UPDATED_NAME)
            .ownerId(UPDATED_OWNER_ID)
            .gender(UPDATED_GENDER)
            .birth(UPDATED_BIRTH)
            .colorId(UPDATED_COLOR_ID);

        restHorseMockMvc.perform(put("/api/horses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHorse)))
            .andExpect(status().isOk());

        // Validate the Horse in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeUpdate);
        Horse testHorse = horseList.get(horseList.size() - 1);
        assertThat(testHorse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHorse.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testHorse.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testHorse.getBirth()).isEqualTo(UPDATED_BIRTH);
        assertThat(testHorse.getColorId()).isEqualTo(UPDATED_COLOR_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingHorse() throws Exception {
        int databaseSizeBeforeUpdate = horseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorseMockMvc.perform(put("/api/horses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isBadRequest());

        // Validate the Horse in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHorse() throws Exception {
        // Initialize the database
        horseRepository.saveAndFlush(horse);

        int databaseSizeBeforeDelete = horseRepository.findAll().size();

        // Delete the horse
        restHorseMockMvc.perform(delete("/api/horses/{id}", horse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
