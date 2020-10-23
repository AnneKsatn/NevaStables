package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.CustomVet;
import com.nevastables.repository.CustomVetRepository;

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

import com.nevastables.domain.enumeration.VetStatus;
/**
 * Integration tests for the {@link CustomVetResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomVetResourceIT {

    private static final Long DEFAULT_HORSE_ID = 1L;
    private static final Long UPDATED_HORSE_ID = 2L;

    private static final VetStatus DEFAULT_STATUS = VetStatus.MISSED;
    private static final VetStatus UPDATED_STATUS = VetStatus.PAID;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_DOCTOR = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private CustomVetRepository customVetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomVetMockMvc;

    private CustomVet customVet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomVet createEntity(EntityManager em) {
        CustomVet customVet = new CustomVet()
            .horseId(DEFAULT_HORSE_ID)
            .status(DEFAULT_STATUS)
            .date(DEFAULT_DATE)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .doctor(DEFAULT_DOCTOR)
            .note(DEFAULT_NOTE);
        return customVet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomVet createUpdatedEntity(EntityManager em) {
        CustomVet customVet = new CustomVet()
            .horseId(UPDATED_HORSE_ID)
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .doctor(UPDATED_DOCTOR)
            .note(UPDATED_NOTE);
        return customVet;
    }

    @BeforeEach
    public void initTest() {
        customVet = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomVet() throws Exception {
        int databaseSizeBeforeCreate = customVetRepository.findAll().size();
        // Create the CustomVet
        restCustomVetMockMvc.perform(post("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customVet)))
            .andExpect(status().isCreated());

        // Validate the CustomVet in the database
        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeCreate + 1);
        CustomVet testCustomVet = customVetList.get(customVetList.size() - 1);
        assertThat(testCustomVet.getHorseId()).isEqualTo(DEFAULT_HORSE_ID);
        assertThat(testCustomVet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomVet.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCustomVet.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCustomVet.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCustomVet.getDoctor()).isEqualTo(DEFAULT_DOCTOR);
        assertThat(testCustomVet.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createCustomVetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customVetRepository.findAll().size();

        // Create the CustomVet with an existing ID
        customVet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomVetMockMvc.perform(post("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customVet)))
            .andExpect(status().isBadRequest());

        // Validate the CustomVet in the database
        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHorseIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = customVetRepository.findAll().size();
        // set the field null
        customVet.setHorseId(null);

        // Create the CustomVet, which fails.


        restCustomVetMockMvc.perform(post("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customVet)))
            .andExpect(status().isBadRequest());

        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = customVetRepository.findAll().size();
        // set the field null
        customVet.setStatus(null);

        // Create the CustomVet, which fails.


        restCustomVetMockMvc.perform(post("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customVet)))
            .andExpect(status().isBadRequest());

        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customVetRepository.findAll().size();
        // set the field null
        customVet.setDate(null);

        // Create the CustomVet, which fails.


        restCustomVetMockMvc.perform(post("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customVet)))
            .andExpect(status().isBadRequest());

        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = customVetRepository.findAll().size();
        // set the field null
        customVet.setTitle(null);

        // Create the CustomVet, which fails.


        restCustomVetMockMvc.perform(post("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customVet)))
            .andExpect(status().isBadRequest());

        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomVets() throws Exception {
        // Initialize the database
        customVetRepository.saveAndFlush(customVet);

        // Get all the customVetList
        restCustomVetMockMvc.perform(get("/api/custom-vets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customVet.getId().intValue())))
            .andExpect(jsonPath("$.[*].horseId").value(hasItem(DEFAULT_HORSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].doctor").value(hasItem(DEFAULT_DOCTOR)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getCustomVet() throws Exception {
        // Initialize the database
        customVetRepository.saveAndFlush(customVet);

        // Get the customVet
        restCustomVetMockMvc.perform(get("/api/custom-vets/{id}", customVet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customVet.getId().intValue()))
            .andExpect(jsonPath("$.horseId").value(DEFAULT_HORSE_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.doctor").value(DEFAULT_DOCTOR))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingCustomVet() throws Exception {
        // Get the customVet
        restCustomVetMockMvc.perform(get("/api/custom-vets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomVet() throws Exception {
        // Initialize the database
        customVetRepository.saveAndFlush(customVet);

        int databaseSizeBeforeUpdate = customVetRepository.findAll().size();

        // Update the customVet
        CustomVet updatedCustomVet = customVetRepository.findById(customVet.getId()).get();
        // Disconnect from session so that the updates on updatedCustomVet are not directly saved in db
        em.detach(updatedCustomVet);
        updatedCustomVet
            .horseId(UPDATED_HORSE_ID)
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .doctor(UPDATED_DOCTOR)
            .note(UPDATED_NOTE);

        restCustomVetMockMvc.perform(put("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomVet)))
            .andExpect(status().isOk());

        // Validate the CustomVet in the database
        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeUpdate);
        CustomVet testCustomVet = customVetList.get(customVetList.size() - 1);
        assertThat(testCustomVet.getHorseId()).isEqualTo(UPDATED_HORSE_ID);
        assertThat(testCustomVet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomVet.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCustomVet.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomVet.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCustomVet.getDoctor()).isEqualTo(UPDATED_DOCTOR);
        assertThat(testCustomVet.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomVet() throws Exception {
        int databaseSizeBeforeUpdate = customVetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomVetMockMvc.perform(put("/api/custom-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customVet)))
            .andExpect(status().isBadRequest());

        // Validate the CustomVet in the database
        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomVet() throws Exception {
        // Initialize the database
        customVetRepository.saveAndFlush(customVet);

        int databaseSizeBeforeDelete = customVetRepository.findAll().size();

        // Delete the customVet
        restCustomVetMockMvc.perform(delete("/api/custom-vets/{id}", customVet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomVet> customVetList = customVetRepository.findAll();
        assertThat(customVetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
