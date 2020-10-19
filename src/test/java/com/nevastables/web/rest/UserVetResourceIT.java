package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.UserVet;
import com.nevastables.repository.UserVetRepository;

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
 * Integration tests for the {@link UserVetResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserVetResourceIT {

    private static final Long DEFAULT_HORSE_ID = 1L;
    private static final Long UPDATED_HORSE_ID = 2L;

    private static final Long DEFAULT_USER_VET_INFO_ID = 1L;
    private static final Long UPDATED_USER_VET_INFO_ID = 2L;

    private static final VetStatus DEFAULT_STATUS = VetStatus.MISSED;
    private static final VetStatus UPDATED_STATUS = VetStatus.PAID;

    @Autowired
    private UserVetRepository userVetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserVetMockMvc;

    private UserVet userVet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVet createEntity(EntityManager em) {
        UserVet userVet = new UserVet()
            .horseId(DEFAULT_HORSE_ID)
            .userVetInfoId(DEFAULT_USER_VET_INFO_ID)
            .status(DEFAULT_STATUS);
        return userVet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVet createUpdatedEntity(EntityManager em) {
        UserVet userVet = new UserVet()
            .horseId(UPDATED_HORSE_ID)
            .userVetInfoId(UPDATED_USER_VET_INFO_ID)
            .status(UPDATED_STATUS);
        return userVet;
    }

    @BeforeEach
    public void initTest() {
        userVet = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserVet() throws Exception {
        int databaseSizeBeforeCreate = userVetRepository.findAll().size();
        // Create the UserVet
        restUserVetMockMvc.perform(post("/api/user-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVet)))
            .andExpect(status().isCreated());

        // Validate the UserVet in the database
        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeCreate + 1);
        UserVet testUserVet = userVetList.get(userVetList.size() - 1);
        assertThat(testUserVet.getHorseId()).isEqualTo(DEFAULT_HORSE_ID);
        assertThat(testUserVet.getUserVetInfoId()).isEqualTo(DEFAULT_USER_VET_INFO_ID);
        assertThat(testUserVet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createUserVetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userVetRepository.findAll().size();

        // Create the UserVet with an existing ID
        userVet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserVetMockMvc.perform(post("/api/user-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVet)))
            .andExpect(status().isBadRequest());

        // Validate the UserVet in the database
        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHorseIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVetRepository.findAll().size();
        // set the field null
        userVet.setHorseId(null);

        // Create the UserVet, which fails.


        restUserVetMockMvc.perform(post("/api/user-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVet)))
            .andExpect(status().isBadRequest());

        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserVetInfoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVetRepository.findAll().size();
        // set the field null
        userVet.setUserVetInfoId(null);

        // Create the UserVet, which fails.


        restUserVetMockMvc.perform(post("/api/user-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVet)))
            .andExpect(status().isBadRequest());

        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = userVetRepository.findAll().size();
        // set the field null
        userVet.setStatus(null);

        // Create the UserVet, which fails.


        restUserVetMockMvc.perform(post("/api/user-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVet)))
            .andExpect(status().isBadRequest());

        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserVets() throws Exception {
        // Initialize the database
        userVetRepository.saveAndFlush(userVet);

        // Get all the userVetList
        restUserVetMockMvc.perform(get("/api/user-vets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userVet.getId().intValue())))
            .andExpect(jsonPath("$.[*].horseId").value(hasItem(DEFAULT_HORSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].userVetInfoId").value(hasItem(DEFAULT_USER_VET_INFO_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getUserVet() throws Exception {
        // Initialize the database
        userVetRepository.saveAndFlush(userVet);

        // Get the userVet
        restUserVetMockMvc.perform(get("/api/user-vets/{id}", userVet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userVet.getId().intValue()))
            .andExpect(jsonPath("$.horseId").value(DEFAULT_HORSE_ID.intValue()))
            .andExpect(jsonPath("$.userVetInfoId").value(DEFAULT_USER_VET_INFO_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserVet() throws Exception {
        // Get the userVet
        restUserVetMockMvc.perform(get("/api/user-vets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserVet() throws Exception {
        // Initialize the database
        userVetRepository.saveAndFlush(userVet);

        int databaseSizeBeforeUpdate = userVetRepository.findAll().size();

        // Update the userVet
        UserVet updatedUserVet = userVetRepository.findById(userVet.getId()).get();
        // Disconnect from session so that the updates on updatedUserVet are not directly saved in db
        em.detach(updatedUserVet);
        updatedUserVet
            .horseId(UPDATED_HORSE_ID)
            .userVetInfoId(UPDATED_USER_VET_INFO_ID)
            .status(UPDATED_STATUS);

        restUserVetMockMvc.perform(put("/api/user-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserVet)))
            .andExpect(status().isOk());

        // Validate the UserVet in the database
        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeUpdate);
        UserVet testUserVet = userVetList.get(userVetList.size() - 1);
        assertThat(testUserVet.getHorseId()).isEqualTo(UPDATED_HORSE_ID);
        assertThat(testUserVet.getUserVetInfoId()).isEqualTo(UPDATED_USER_VET_INFO_ID);
        assertThat(testUserVet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserVet() throws Exception {
        int databaseSizeBeforeUpdate = userVetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserVetMockMvc.perform(put("/api/user-vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userVet)))
            .andExpect(status().isBadRequest());

        // Validate the UserVet in the database
        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserVet() throws Exception {
        // Initialize the database
        userVetRepository.saveAndFlush(userVet);

        int databaseSizeBeforeDelete = userVetRepository.findAll().size();

        // Delete the userVet
        restUserVetMockMvc.perform(delete("/api/user-vets/{id}", userVet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserVet> userVetList = userVetRepository.findAll();
        assertThat(userVetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
