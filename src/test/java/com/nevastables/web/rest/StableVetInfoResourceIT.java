package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.StableVetInfo;
import com.nevastables.repository.StableVetInfoRepository;

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
 * Integration tests for the {@link StableVetInfoResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StableVetInfoResourceIT {

    private static final Long DEFAULT_STABLE_ID = 1L;
    private static final Long UPDATED_STABLE_ID = 2L;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    @Autowired
    private StableVetInfoRepository stableVetInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStableVetInfoMockMvc;

    private StableVetInfo stableVetInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StableVetInfo createEntity(EntityManager em) {
        StableVetInfo stableVetInfo = new StableVetInfo()
            .stableId(DEFAULT_STABLE_ID)
            .date(DEFAULT_DATE)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE);
        return stableVetInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StableVetInfo createUpdatedEntity(EntityManager em) {
        StableVetInfo stableVetInfo = new StableVetInfo()
            .stableId(UPDATED_STABLE_ID)
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE);
        return stableVetInfo;
    }

    @BeforeEach
    public void initTest() {
        stableVetInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createStableVetInfo() throws Exception {
        int databaseSizeBeforeCreate = stableVetInfoRepository.findAll().size();
        // Create the StableVetInfo
        restStableVetInfoMockMvc.perform(post("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVetInfo)))
            .andExpect(status().isCreated());

        // Validate the StableVetInfo in the database
        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeCreate + 1);
        StableVetInfo testStableVetInfo = stableVetInfoList.get(stableVetInfoList.size() - 1);
        assertThat(testStableVetInfo.getStableId()).isEqualTo(DEFAULT_STABLE_ID);
        assertThat(testStableVetInfo.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStableVetInfo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStableVetInfo.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createStableVetInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stableVetInfoRepository.findAll().size();

        // Create the StableVetInfo with an existing ID
        stableVetInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStableVetInfoMockMvc.perform(post("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVetInfo)))
            .andExpect(status().isBadRequest());

        // Validate the StableVetInfo in the database
        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStableIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableVetInfoRepository.findAll().size();
        // set the field null
        stableVetInfo.setStableId(null);

        // Create the StableVetInfo, which fails.


        restStableVetInfoMockMvc.perform(post("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVetInfo)))
            .andExpect(status().isBadRequest());

        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableVetInfoRepository.findAll().size();
        // set the field null
        stableVetInfo.setDate(null);

        // Create the StableVetInfo, which fails.


        restStableVetInfoMockMvc.perform(post("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVetInfo)))
            .andExpect(status().isBadRequest());

        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableVetInfoRepository.findAll().size();
        // set the field null
        stableVetInfo.setTitle(null);

        // Create the StableVetInfo, which fails.


        restStableVetInfoMockMvc.perform(post("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVetInfo)))
            .andExpect(status().isBadRequest());

        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stableVetInfoRepository.findAll().size();
        // set the field null
        stableVetInfo.setPrice(null);

        // Create the StableVetInfo, which fails.


        restStableVetInfoMockMvc.perform(post("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVetInfo)))
            .andExpect(status().isBadRequest());

        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStableVetInfos() throws Exception {
        // Initialize the database
        stableVetInfoRepository.saveAndFlush(stableVetInfo);

        // Get all the stableVetInfoList
        restStableVetInfoMockMvc.perform(get("/api/stable-vet-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stableVetInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].stableId").value(hasItem(DEFAULT_STABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getStableVetInfo() throws Exception {
        // Initialize the database
        stableVetInfoRepository.saveAndFlush(stableVetInfo);

        // Get the stableVetInfo
        restStableVetInfoMockMvc.perform(get("/api/stable-vet-infos/{id}", stableVetInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stableVetInfo.getId().intValue()))
            .andExpect(jsonPath("$.stableId").value(DEFAULT_STABLE_ID.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStableVetInfo() throws Exception {
        // Get the stableVetInfo
        restStableVetInfoMockMvc.perform(get("/api/stable-vet-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStableVetInfo() throws Exception {
        // Initialize the database
        stableVetInfoRepository.saveAndFlush(stableVetInfo);

        int databaseSizeBeforeUpdate = stableVetInfoRepository.findAll().size();

        // Update the stableVetInfo
        StableVetInfo updatedStableVetInfo = stableVetInfoRepository.findById(stableVetInfo.getId()).get();
        // Disconnect from session so that the updates on updatedStableVetInfo are not directly saved in db
        em.detach(updatedStableVetInfo);
        updatedStableVetInfo
            .stableId(UPDATED_STABLE_ID)
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE);

        restStableVetInfoMockMvc.perform(put("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStableVetInfo)))
            .andExpect(status().isOk());

        // Validate the StableVetInfo in the database
        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeUpdate);
        StableVetInfo testStableVetInfo = stableVetInfoList.get(stableVetInfoList.size() - 1);
        assertThat(testStableVetInfo.getStableId()).isEqualTo(UPDATED_STABLE_ID);
        assertThat(testStableVetInfo.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStableVetInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStableVetInfo.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingStableVetInfo() throws Exception {
        int databaseSizeBeforeUpdate = stableVetInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStableVetInfoMockMvc.perform(put("/api/stable-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stableVetInfo)))
            .andExpect(status().isBadRequest());

        // Validate the StableVetInfo in the database
        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStableVetInfo() throws Exception {
        // Initialize the database
        stableVetInfoRepository.saveAndFlush(stableVetInfo);

        int databaseSizeBeforeDelete = stableVetInfoRepository.findAll().size();

        // Delete the stableVetInfo
        restStableVetInfoMockMvc.perform(delete("/api/stable-vet-infos/{id}", stableVetInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StableVetInfo> stableVetInfoList = stableVetInfoRepository.findAll();
        assertThat(stableVetInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
