package com.mblearning.mblms.web.rest;

import com.mblearning.mblms.MblmsApp;
import com.mblearning.mblms.domain.UserProperties;
import com.mblearning.mblms.repository.UserPropertiesRepository;

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
 * Test class for the UserPropertiesResource REST controller.
 *
 * @see UserPropertiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MblmsApp.class)
public class UserPropertiesResourceIntTest {
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";

    private static final Boolean DEFAULT_EDUCATOR = false;
    private static final Boolean UPDATED_EDUCATOR = true;

    private static final Float DEFAULT_GPA = 1F;
    private static final Float UPDATED_GPA = 2F;
    private static final String DEFAULT_FOCUS = "AAAAA";
    private static final String UPDATED_FOCUS = "BBBBB";
    private static final String DEFAULT_PROGRAM = "AAAAA";
    private static final String UPDATED_PROGRAM = "BBBBB";

    @Inject
    private UserPropertiesRepository userPropertiesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserPropertiesMockMvc;

    private UserProperties userProperties;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserPropertiesResource userPropertiesResource = new UserPropertiesResource();
        ReflectionTestUtils.setField(userPropertiesResource, "userPropertiesRepository", userPropertiesRepository);
        this.restUserPropertiesMockMvc = MockMvcBuilders.standaloneSetup(userPropertiesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProperties createEntity(EntityManager em) {
        UserProperties userProperties = new UserProperties();
        userProperties = new UserProperties()
                .status(DEFAULT_STATUS)
                .educator(DEFAULT_EDUCATOR)
                .gpa(DEFAULT_GPA)
                .focus(DEFAULT_FOCUS)
                .program(DEFAULT_PROGRAM);
        return userProperties;
    }

    @Before
    public void initTest() {
        userProperties = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProperties() throws Exception {
        int databaseSizeBeforeCreate = userPropertiesRepository.findAll().size();

        // Create the UserProperties

        restUserPropertiesMockMvc.perform(post("/api/user-properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userProperties)))
                .andExpect(status().isCreated());

        // Validate the UserProperties in the database
        List<UserProperties> userProperties = userPropertiesRepository.findAll();
        assertThat(userProperties).hasSize(databaseSizeBeforeCreate + 1);
        UserProperties testUserProperties = userProperties.get(userProperties.size() - 1);
        assertThat(testUserProperties.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserProperties.isEducator()).isEqualTo(DEFAULT_EDUCATOR);
        assertThat(testUserProperties.getGpa()).isEqualTo(DEFAULT_GPA);
        assertThat(testUserProperties.getFocus()).isEqualTo(DEFAULT_FOCUS);
        assertThat(testUserProperties.getProgram()).isEqualTo(DEFAULT_PROGRAM);
    }

    @Test
    @Transactional
    public void getAllUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);

        // Get all the userProperties
        restUserPropertiesMockMvc.perform(get("/api/user-properties?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userProperties.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].educator").value(hasItem(DEFAULT_EDUCATOR.booleanValue())))
                .andExpect(jsonPath("$.[*].gpa").value(hasItem(DEFAULT_GPA.doubleValue())))
                .andExpect(jsonPath("$.[*].focus").value(hasItem(DEFAULT_FOCUS.toString())))
                .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())));
    }

    @Test
    @Transactional
    public void getUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);

        // Get the userProperties
        restUserPropertiesMockMvc.perform(get("/api/user-properties/{id}", userProperties.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userProperties.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.educator").value(DEFAULT_EDUCATOR.booleanValue()))
            .andExpect(jsonPath("$.gpa").value(DEFAULT_GPA.doubleValue()))
            .andExpect(jsonPath("$.focus").value(DEFAULT_FOCUS.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserProperties() throws Exception {
        // Get the userProperties
        restUserPropertiesMockMvc.perform(get("/api/user-properties/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);
        int databaseSizeBeforeUpdate = userPropertiesRepository.findAll().size();

        // Update the userProperties
        UserProperties updatedUserProperties = userPropertiesRepository.findOne(userProperties.getId());
        updatedUserProperties
                .status(UPDATED_STATUS)
                .educator(UPDATED_EDUCATOR)
                .gpa(UPDATED_GPA)
                .focus(UPDATED_FOCUS)
                .program(UPDATED_PROGRAM);

        restUserPropertiesMockMvc.perform(put("/api/user-properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUserProperties)))
                .andExpect(status().isOk());

        // Validate the UserProperties in the database
        List<UserProperties> userProperties = userPropertiesRepository.findAll();
        assertThat(userProperties).hasSize(databaseSizeBeforeUpdate);
        UserProperties testUserProperties = userProperties.get(userProperties.size() - 1);
        assertThat(testUserProperties.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserProperties.isEducator()).isEqualTo(UPDATED_EDUCATOR);
        assertThat(testUserProperties.getGpa()).isEqualTo(UPDATED_GPA);
        assertThat(testUserProperties.getFocus()).isEqualTo(UPDATED_FOCUS);
        assertThat(testUserProperties.getProgram()).isEqualTo(UPDATED_PROGRAM);
    }

    @Test
    @Transactional
    public void deleteUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);
        int databaseSizeBeforeDelete = userPropertiesRepository.findAll().size();

        // Get the userProperties
        restUserPropertiesMockMvc.perform(delete("/api/user-properties/{id}", userProperties.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserProperties> userProperties = userPropertiesRepository.findAll();
        assertThat(userProperties).hasSize(databaseSizeBeforeDelete - 1);
    }
}
