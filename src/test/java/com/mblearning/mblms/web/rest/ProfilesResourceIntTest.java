package com.mblearning.mblms.web.rest;

import com.mblearning.mblms.MblmsApp;
import com.mblearning.mblms.domain.Profiles;
import com.mblearning.mblms.repository.ProfilesRepository;

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
 * Test class for the ProfilesResource REST controller.
 *
 * @see ProfilesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MblmsApp.class)
public class ProfilesResourceIntTest {
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ProfilesRepository profilesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProfilesMockMvc;

    private Profiles profiles;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfilesResource profilesResource = new ProfilesResource();
        ReflectionTestUtils.setField(profilesResource, "profilesRepository", profilesRepository);
        this.restProfilesMockMvc = MockMvcBuilders.standaloneSetup(profilesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profiles createEntity(EntityManager em) {
        Profiles profiles = new Profiles();
        profiles = new Profiles()
                .name(DEFAULT_NAME);
        return profiles;
    }

    @Before
    public void initTest() {
        profiles = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfiles() throws Exception {
        int databaseSizeBeforeCreate = profilesRepository.findAll().size();

        // Create the Profiles

        restProfilesMockMvc.perform(post("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profiles)))
                .andExpect(status().isCreated());

        // Validate the Profiles in the database
        List<Profiles> profiles = profilesRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeCreate + 1);
        Profiles testProfiles = profiles.get(profiles.size() - 1);
        assertThat(testProfiles.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = profilesRepository.findAll().size();
        // set the field null
        profiles.setName(null);

        // Create the Profiles, which fails.

        restProfilesMockMvc.perform(post("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profiles)))
                .andExpect(status().isBadRequest());

        List<Profiles> profiles = profilesRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);

        // Get all the profiles
        restProfilesMockMvc.perform(get("/api/profiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(profiles.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);

        // Get the profiles
        restProfilesMockMvc.perform(get("/api/profiles/{id}", profiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profiles.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfiles() throws Exception {
        // Get the profiles
        restProfilesMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);
        int databaseSizeBeforeUpdate = profilesRepository.findAll().size();

        // Update the profiles
        Profiles updatedProfiles = profilesRepository.findOne(profiles.getId());
        updatedProfiles
                .name(UPDATED_NAME);

        restProfilesMockMvc.perform(put("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProfiles)))
                .andExpect(status().isOk());

        // Validate the Profiles in the database
        List<Profiles> profiles = profilesRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeUpdate);
        Profiles testProfiles = profiles.get(profiles.size() - 1);
        assertThat(testProfiles.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteProfiles() throws Exception {
        // Initialize the database
        profilesRepository.saveAndFlush(profiles);
        int databaseSizeBeforeDelete = profilesRepository.findAll().size();

        // Get the profiles
        restProfilesMockMvc.perform(delete("/api/profiles/{id}", profiles.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Profiles> profiles = profilesRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
