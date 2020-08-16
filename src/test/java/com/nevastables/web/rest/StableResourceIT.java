package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.Stable;
import com.nevastables.repository.StableRepository;

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
 * Integration tests for the {@link StableResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StableResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_MANAGER_ID = 1L;
    private static final Long UPDATED_MANAGER_ID = 2L;

    @Autowired
    private StableRepository stableRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStableMockMvc;

    private Stable stable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stable createEntity(EntityManager em) {
        Stable stable = new Stable()
            .title(DEFAULT_TITLE)
            .managerId(DEFAULT_MANAGER_ID);
        return stable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stable createUpdatedEntity(EntityManager em) {
        Stable stable = new Stable()
            .title(UPDATED_TITLE)
            .managerId(UPDATED_MANAGER_ID);
        return stable;
    }

    @BeforeEach
    public void initTest() {
        stable = createEntity(em);
    }

    @Test
    @Transactional
    public void createStable() throws Exception {
        int databaseSizeBeforeCreate = stableRepository.findAll().size();
        // Create the Stable
        restStableMockMvc.perform(post("/api/stables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stable)))
            .andExpect(status().isCreated());

        // Validate the Stable in the database
        List<Stable> stableList = stableRepository.findAll();
        assertThat(stableList).hasSize(databaseSizeBeforeCreate + 1);
        Stable testStable = stableList.get(stableList.size() - 1);
        assertThat(testStable.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStable.getManagerId()).isEqualTo(DEFAULT_MANAGER_ID);
    }

    @Test
    @Transactional
    public void createStableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stableRepository.findAll().size();

        // Create the Stable with an existing ID
        stable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStableMockMvc.perform(post("/api/stables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stable)))
            .andExpect(status().isBadRequest());

        // Validate the Stable in the database
        List<Stable> stableList = stableRepository.findAll();
        assertThat(stableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableRepository.findAll().size();
        // set the field null
        stable.setTitle(null);

        // Create the Stable, which fails.


        restStableMockMvc.perform(post("/api/stables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stable)))
            .andExpect(status().isBadRequest());

        List<Stable> stableList = stableRepository.findAll();
        assertThat(stableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkManagerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableRepository.findAll().size();
        // set the field null
        stable.setManagerId(null);

        // Create the Stable, which fails.


        restStableMockMvc.perform(post("/api/stables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stable)))
            .andExpect(status().isBadRequest());

        List<Stable> stableList = stableRepository.findAll();
        assertThat(stableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStables() throws Exception {
        // Initialize the database
        stableRepository.saveAndFlush(stable);

        // Get all the stableList
        restStableMockMvc.perform(get("/api/stables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stable.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].managerId").value(hasItem(DEFAULT_MANAGER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStable() throws Exception {
        // Initialize the database
        stableRepository.saveAndFlush(stable);

        // Get the stable
        restStableMockMvc.perform(get("/api/stables/{id}", stable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stable.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.managerId").value(DEFAULT_MANAGER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStable() throws Exception {
        // Get the stable
        restStableMockMvc.perform(get("/api/stables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStable() throws Exception {
        // Initialize the database
        stableRepository.saveAndFlush(stable);

        int databaseSizeBeforeUpdate = stableRepository.findAll().size();

        // Update the stable
        Stable updatedStable = stableRepository.findById(stable.getId()).get();
        // Disconnect from session so that the updates on updatedStable are not directly saved in db
        em.detach(updatedStable);
        updatedStable
            .title(UPDATED_TITLE)
            .managerId(UPDATED_MANAGER_ID);

        restStableMockMvc.perform(put("/api/stables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStable)))
            .andExpect(status().isOk());

        // Validate the Stable in the database
        List<Stable> stableList = stableRepository.findAll();
        assertThat(stableList).hasSize(databaseSizeBeforeUpdate);
        Stable testStable = stableList.get(stableList.size() - 1);
        assertThat(testStable.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStable.getManagerId()).isEqualTo(UPDATED_MANAGER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStable() throws Exception {
        int databaseSizeBeforeUpdate = stableRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStableMockMvc.perform(put("/api/stables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stable)))
            .andExpect(status().isBadRequest());

        // Validate the Stable in the database
        List<Stable> stableList = stableRepository.findAll();
        assertThat(stableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStable() throws Exception {
        // Initialize the database
        stableRepository.saveAndFlush(stable);

        int databaseSizeBeforeDelete = stableRepository.findAll().size();

        // Delete the stable
        restStableMockMvc.perform(delete("/api/stables/{id}", stable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stable> stableList = stableRepository.findAll();
        assertThat(stableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
