package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.UserVetInfo;
import com.nevastables.repository.UserVetInfoRepository;

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
 * Integration tests for the {@link UserVetInfoResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserVetInfoResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_DOCTOR = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR = "BBBBBBBBBB";

    @Autowired
    private UserVetInfoRepository userVetInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserVetInfoMockMvc;

    private UserVetInfo userVetInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVetInfo createEntity(EntityManager em) {
        UserVetInfo userVetInfo = new UserVetInfo()
            .date(DEFAULT_DATE)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .doctor(DEFAULT_DOCTOR);
        return userVetInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVetInfo createUpdatedEntity(EntityManager em) {
        UserVetInfo userVetInfo = new UserVetInfo()
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .doctor(UPDATED_DOCTOR);
        return userVetInfo;
    }

    @BeforeEach
    public void initTest() {
        userVetInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserVetInfo() throws Exception {
        int databaseSizeBeforeCreate = userVetInfoRepository.findAll().size();
        // Create the UserVetInfo
        restUserVetInfoMockMvc.perform(post("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVetInfo)))
            .andExpect(status().isCreated());

        // Validate the UserVetInfo in the database
        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserVetInfo testUserVetInfo = userVetInfoList.get(userVetInfoList.size() - 1);
        assertThat(testUserVetInfo.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testUserVetInfo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUserVetInfo.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testUserVetInfo.getDoctor()).isEqualTo(DEFAULT_DOCTOR);
    }

    @Test
    @Transactional
    public void createUserVetInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userVetInfoRepository.findAll().size();

        // Create the UserVetInfo with an existing ID
        userVetInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserVetInfoMockMvc.perform(post("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVetInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserVetInfo in the database
        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVetInfoRepository.findAll().size();
        // set the field null
        userVetInfo.setDate(null);

        // Create the UserVetInfo, which fails.


        restUserVetInfoMockMvc.perform(post("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVetInfo)))
            .andExpect(status().isBadRequest());

        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVetInfoRepository.findAll().size();
        // set the field null
        userVetInfo.setTitle(null);

        // Create the UserVetInfo, which fails.


        restUserVetInfoMockMvc.perform(post("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVetInfo)))
            .andExpect(status().isBadRequest());

        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVetInfoRepository.findAll().size();
        // set the field null
        userVetInfo.setPrice(null);

        // Create the UserVetInfo, which fails.


        restUserVetInfoMockMvc.perform(post("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVetInfo)))
            .andExpect(status().isBadRequest());

        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDoctorIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVetInfoRepository.findAll().size();
        // set the field null
        userVetInfo.setDoctor(null);

        // Create the UserVetInfo, which fails.


        restUserVetInfoMockMvc.perform(post("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVetInfo)))
            .andExpect(status().isBadRequest());

        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserVetInfos() throws Exception {
        // Initialize the database
        userVetInfoRepository.saveAndFlush(userVetInfo);

        // Get all the userVetInfoList
        restUserVetInfoMockMvc.perform(get("/api/user-vet-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userVetInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].doctor").value(hasItem(DEFAULT_DOCTOR)));
    }
    
    @Test
    @Transactional
    public void getUserVetInfo() throws Exception {
        // Initialize the database
        userVetInfoRepository.saveAndFlush(userVetInfo);

        // Get the userVetInfo
        restUserVetInfoMockMvc.perform(get("/api/user-vet-infos/{id}", userVetInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userVetInfo.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.doctor").value(DEFAULT_DOCTOR));
    }
    @Test
    @Transactional
    public void getNonExistingUserVetInfo() throws Exception {
        // Get the userVetInfo
        restUserVetInfoMockMvc.perform(get("/api/user-vet-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserVetInfo() throws Exception {
        // Initialize the database
        userVetInfoRepository.saveAndFlush(userVetInfo);

        int databaseSizeBeforeUpdate = userVetInfoRepository.findAll().size();

        // Update the userVetInfo
        UserVetInfo updatedUserVetInfo = userVetInfoRepository.findById(userVetInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserVetInfo are not directly saved in db
        em.detach(updatedUserVetInfo);
        updatedUserVetInfo
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .doctor(UPDATED_DOCTOR);

        restUserVetInfoMockMvc.perform(put("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserVetInfo)))
            .andExpect(status().isOk());

        // Validate the UserVetInfo in the database
        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeUpdate);
        UserVetInfo testUserVetInfo = userVetInfoList.get(userVetInfoList.size() - 1);
        assertThat(testUserVetInfo.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testUserVetInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUserVetInfo.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testUserVetInfo.getDoctor()).isEqualTo(UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingUserVetInfo() throws Exception {
        int databaseSizeBeforeUpdate = userVetInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserVetInfoMockMvc.perform(put("/api/user-vet-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVetInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserVetInfo in the database
        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserVetInfo() throws Exception {
        // Initialize the database
        userVetInfoRepository.saveAndFlush(userVetInfo);

        int databaseSizeBeforeDelete = userVetInfoRepository.findAll().size();

        // Delete the userVetInfo
        restUserVetInfoMockMvc.perform(delete("/api/user-vet-infos/{id}", userVetInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserVetInfo> userVetInfoList = userVetInfoRepository.findAll();
        assertThat(userVetInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
