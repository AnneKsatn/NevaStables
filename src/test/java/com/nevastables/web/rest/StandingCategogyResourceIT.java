package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.StandingCategogy;
import com.nevastables.repository.StandingCategogyRepository;

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

/**
 * Integration tests for the {@link StandingCategogyResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StandingCategogyResourceIT {

    private static final Long DEFAULT_STABLE_ID = 1L;
    private static final Long UPDATED_STABLE_ID = 2L;

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private StandingCategogyRepository standingCategogyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStandingCategogyMockMvc;

    private StandingCategogy standingCategogy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandingCategogy createEntity(EntityManager em) {
        StandingCategogy standingCategogy = new StandingCategogy()
            .stableId(DEFAULT_STABLE_ID)
            .price(DEFAULT_PRICE)
            .title(DEFAULT_TITLE);
        return standingCategogy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandingCategogy createUpdatedEntity(EntityManager em) {
        StandingCategogy standingCategogy = new StandingCategogy()
            .stableId(UPDATED_STABLE_ID)
            .price(UPDATED_PRICE)
            .title(UPDATED_TITLE);
        return standingCategogy;
    }

    @BeforeEach
    public void initTest() {
        standingCategogy = createEntity(em);
    }

    @Test
    @Transactional
    public void createStandingCategogy() throws Exception {
        int databaseSizeBeforeCreate = standingCategogyRepository.findAll().size();
        // Create the StandingCategogy
        restStandingCategogyMockMvc.perform(post("/api/standing-categogies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(standingCategogy)))
            .andExpect(status().isCreated());

        // Validate the StandingCategogy in the database
        List<StandingCategogy> standingCategogyList = standingCategogyRepository.findAll();
        assertThat(standingCategogyList).hasSize(databaseSizeBeforeCreate + 1);
        StandingCategogy testStandingCategogy = standingCategogyList.get(standingCategogyList.size() - 1);
        assertThat(testStandingCategogy.getStableId()).isEqualTo(DEFAULT_STABLE_ID);
        assertThat(testStandingCategogy.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testStandingCategogy.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createStandingCategogyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = standingCategogyRepository.findAll().size();

        // Create the StandingCategogy with an existing ID
        standingCategogy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandingCategogyMockMvc.perform(post("/api/standing-categogies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(standingCategogy)))
            .andExpect(status().isBadRequest());

        // Validate the StandingCategogy in the database
        List<StandingCategogy> standingCategogyList = standingCategogyRepository.findAll();
        assertThat(standingCategogyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStableIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = standingCategogyRepository.findAll().size();
        // set the field null
        standingCategogy.setStableId(null);

        // Create the StandingCategogy, which fails.


        restStandingCategogyMockMvc.perform(post("/api/standing-categogies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(standingCategogy)))
            .andExpect(status().isBadRequest());

        List<StandingCategogy> standingCategogyList = standingCategogyRepository.findAll();
        assertThat(standingCategogyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStandingCategogies() throws Exception {
        // Initialize the database
        standingCategogyRepository.saveAndFlush(standingCategogy);

        // Get all the standingCategogyList
        restStandingCategogyMockMvc.perform(get("/api/standing-categogies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standingCategogy.getId().intValue())))
            .andExpect(jsonPath("$.[*].stableId").value(hasItem(DEFAULT_STABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getStandingCategogy() throws Exception {
        // Initialize the database
        standingCategogyRepository.saveAndFlush(standingCategogy);

        // Get the standingCategogy
        restStandingCategogyMockMvc.perform(get("/api/standing-categogies/{id}", standingCategogy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(standingCategogy.getId().intValue()))
            .andExpect(jsonPath("$.stableId").value(DEFAULT_STABLE_ID.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }
    @Test
    @Transactional
    public void getNonExistingStandingCategogy() throws Exception {
        // Get the standingCategogy
        restStandingCategogyMockMvc.perform(get("/api/standing-categogies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStandingCategogy() throws Exception {
        // Initialize the database
        standingCategogyRepository.saveAndFlush(standingCategogy);

        int databaseSizeBeforeUpdate = standingCategogyRepository.findAll().size();

        // Update the standingCategogy
        StandingCategogy updatedStandingCategogy = standingCategogyRepository.findById(standingCategogy.getId()).get();
        // Disconnect from session so that the updates on updatedStandingCategogy are not directly saved in db
        em.detach(updatedStandingCategogy);
        updatedStandingCategogy
            .stableId(UPDATED_STABLE_ID)
            .price(UPDATED_PRICE)
            .title(UPDATED_TITLE);

        restStandingCategogyMockMvc.perform(put("/api/standing-categogies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStandingCategogy)))
            .andExpect(status().isOk());

        // Validate the StandingCategogy in the database
        List<StandingCategogy> standingCategogyList = standingCategogyRepository.findAll();
        assertThat(standingCategogyList).hasSize(databaseSizeBeforeUpdate);
        StandingCategogy testStandingCategogy = standingCategogyList.get(standingCategogyList.size() - 1);
        assertThat(testStandingCategogy.getStableId()).isEqualTo(UPDATED_STABLE_ID);
        assertThat(testStandingCategogy.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testStandingCategogy.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingStandingCategogy() throws Exception {
        int databaseSizeBeforeUpdate = standingCategogyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandingCategogyMockMvc.perform(put("/api/standing-categogies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(standingCategogy)))
            .andExpect(status().isBadRequest());

        // Validate the StandingCategogy in the database
        List<StandingCategogy> standingCategogyList = standingCategogyRepository.findAll();
        assertThat(standingCategogyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStandingCategogy() throws Exception {
        // Initialize the database
        standingCategogyRepository.saveAndFlush(standingCategogy);

        int databaseSizeBeforeDelete = standingCategogyRepository.findAll().size();

        // Delete the standingCategogy
        restStandingCategogyMockMvc.perform(delete("/api/standing-categogies/{id}", standingCategogy.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StandingCategogy> standingCategogyList = standingCategogyRepository.findAll();
        assertThat(standingCategogyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
