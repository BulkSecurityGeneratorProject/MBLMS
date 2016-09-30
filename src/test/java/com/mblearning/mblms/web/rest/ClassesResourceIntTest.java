package com.mblearning.mblms.web.rest;

import com.mblearning.mblms.MblmsApp;
import com.mblearning.mblms.domain.Classes;
import com.mblearning.mblms.repository.ClassesRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClassesResource REST controller.
 *
 * @see ClassesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MblmsApp.class)
public class ClassesResourceIntTest {
    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    @Inject
    private ClassesRepository classesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClassesMockMvc;

    private Classes classes;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassesResource classesResource = new ClassesResource();
        ReflectionTestUtils.setField(classesResource, "classesRepository", classesRepository);
        this.restClassesMockMvc = MockMvcBuilders.standaloneSetup(classesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classes createEntity(EntityManager em) {
        Classes classes = new Classes();
        classes = new Classes()
                .name(DEFAULT_NAME)
                .code(DEFAULT_CODE);
        return classes;
    }

    @Before
    public void initTest() {
        classes = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasses() throws Exception {
        int databaseSizeBeforeCreate = classesRepository.findAll().size();

        // Create the Classes

        restClassesMockMvc.perform(post("/api/classes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classes)))
                .andExpect(status().isCreated());

        // Validate the Classes in the database
        List<Classes> classes = classesRepository.findAll();
        assertThat(classes).hasSize(databaseSizeBeforeCreate + 1);
        Classes testClasses = classes.get(classes.size() - 1);
        assertThat(testClasses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClasses.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = classesRepository.findAll().size();
        // set the field null
        classes.setName(null);

        // Create the Classes, which fails.

        restClassesMockMvc.perform(post("/api/classes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classes)))
                .andExpect(status().isBadRequest());

        List<Classes> classes = classesRepository.findAll();
        assertThat(classes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);

        // Get all the classes
        restClassesMockMvc.perform(get("/api/classes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(classes.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);

        // Get the classes
        restClassesMockMvc.perform(get("/api/classes/{id}", classes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClasses() throws Exception {
        // Get the classes
        restClassesMockMvc.perform(get("/api/classes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);
        int databaseSizeBeforeUpdate = classesRepository.findAll().size();

        // Update the classes
        Classes updatedClasses = classesRepository.findOne(classes.getId());
        updatedClasses
                .name(UPDATED_NAME)
                .code(UPDATED_CODE);

        restClassesMockMvc.perform(put("/api/classes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClasses)))
                .andExpect(status().isOk());

        // Validate the Classes in the database
        List<Classes> classes = classesRepository.findAll();
        assertThat(classes).hasSize(databaseSizeBeforeUpdate);
        Classes testClasses = classes.get(classes.size() - 1);
        assertThat(testClasses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClasses.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);
        int databaseSizeBeforeDelete = classesRepository.findAll().size();

        // Get the classes
        restClassesMockMvc.perform(delete("/api/classes/{id}", classes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Classes> classes = classesRepository.findAll();
        assertThat(classes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
