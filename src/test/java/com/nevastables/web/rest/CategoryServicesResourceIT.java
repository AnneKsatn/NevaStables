package com.nevastables.web.rest;

import com.nevastables.NevaStablesApp;
import com.nevastables.domain.CategoryServices;
import com.nevastables.repository.CategoryServicesRepository;

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
 * Integration tests for the {@link CategoryServicesResource} REST controller.
 */
@SpringBootTest(classes = NevaStablesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryServicesResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private CategoryServicesRepository categoryServicesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryServicesMockMvc;

    private CategoryServices categoryServices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryServices createEntity(EntityManager em) {
        CategoryServices categoryServices = new CategoryServices()
            .categoryId(DEFAULT_CATEGORY_ID)
            .title(DEFAULT_TITLE);
        return categoryServices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryServices createUpdatedEntity(EntityManager em) {
        CategoryServices categoryServices = new CategoryServices()
            .categoryId(UPDATED_CATEGORY_ID)
            .title(UPDATED_TITLE);
        return categoryServices;
    }

    @BeforeEach
    public void initTest() {
        categoryServices = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryServices() throws Exception {
        int databaseSizeBeforeCreate = categoryServicesRepository.findAll().size();
        // Create the CategoryServices
        restCategoryServicesMockMvc.perform(post("/api/category-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryServices)))
            .andExpect(status().isCreated());

        // Validate the CategoryServices in the database
        List<CategoryServices> categoryServicesList = categoryServicesRepository.findAll();
        assertThat(categoryServicesList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryServices testCategoryServices = categoryServicesList.get(categoryServicesList.size() - 1);
        assertThat(testCategoryServices.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryServices.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createCategoryServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryServicesRepository.findAll().size();

        // Create the CategoryServices with an existing ID
        categoryServices.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryServicesMockMvc.perform(post("/api/category-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryServices)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryServices in the database
        List<CategoryServices> categoryServicesList = categoryServicesRepository.findAll();
        assertThat(categoryServicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryServicesRepository.findAll().size();
        // set the field null
        categoryServices.setCategoryId(null);

        // Create the CategoryServices, which fails.


        restCategoryServicesMockMvc.perform(post("/api/category-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryServices)))
            .andExpect(status().isBadRequest());

        List<CategoryServices> categoryServicesList = categoryServicesRepository.findAll();
        assertThat(categoryServicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryServices() throws Exception {
        // Initialize the database
        categoryServicesRepository.saveAndFlush(categoryServices);

        // Get all the categoryServicesList
        restCategoryServicesMockMvc.perform(get("/api/category-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getCategoryServices() throws Exception {
        // Initialize the database
        categoryServicesRepository.saveAndFlush(categoryServices);

        // Get the categoryServices
        restCategoryServicesMockMvc.perform(get("/api/category-services/{id}", categoryServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryServices.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryServices() throws Exception {
        // Get the categoryServices
        restCategoryServicesMockMvc.perform(get("/api/category-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryServices() throws Exception {
        // Initialize the database
        categoryServicesRepository.saveAndFlush(categoryServices);

        int databaseSizeBeforeUpdate = categoryServicesRepository.findAll().size();

        // Update the categoryServices
        CategoryServices updatedCategoryServices = categoryServicesRepository.findById(categoryServices.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryServices are not directly saved in db
        em.detach(updatedCategoryServices);
        updatedCategoryServices
            .categoryId(UPDATED_CATEGORY_ID)
            .title(UPDATED_TITLE);

        restCategoryServicesMockMvc.perform(put("/api/category-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoryServices)))
            .andExpect(status().isOk());

        // Validate the CategoryServices in the database
        List<CategoryServices> categoryServicesList = categoryServicesRepository.findAll();
        assertThat(categoryServicesList).hasSize(databaseSizeBeforeUpdate);
        CategoryServices testCategoryServices = categoryServicesList.get(categoryServicesList.size() - 1);
        assertThat(testCategoryServices.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryServices.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryServices() throws Exception {
        int databaseSizeBeforeUpdate = categoryServicesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryServicesMockMvc.perform(put("/api/category-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryServices)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryServices in the database
        List<CategoryServices> categoryServicesList = categoryServicesRepository.findAll();
        assertThat(categoryServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryServices() throws Exception {
        // Initialize the database
        categoryServicesRepository.saveAndFlush(categoryServices);

        int databaseSizeBeforeDelete = categoryServicesRepository.findAll().size();

        // Delete the categoryServices
        restCategoryServicesMockMvc.perform(delete("/api/category-services/{id}", categoryServices.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryServices> categoryServicesList = categoryServicesRepository.findAll();
        assertThat(categoryServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
