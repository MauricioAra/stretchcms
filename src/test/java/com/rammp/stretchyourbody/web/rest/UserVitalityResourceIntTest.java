package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.UserVitality;
import com.rammp.stretchyourbody.repository.UserVitalityRepository;
import com.rammp.stretchyourbody.service.UserVitalityService;
import com.rammp.stretchyourbody.service.dto.UserVitalityDTO;
import com.rammp.stretchyourbody.service.mapper.UserVitalityMapper;
import com.rammp.stretchyourbody.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserVitalityResource REST controller.
 *
 * @see UserVitalityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class UserVitalityResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_RANGE = 1;
    private static final Integer UPDATED_RANGE = 2;

    @Autowired
    private UserVitalityRepository userVitalityRepository;

    @Autowired
    private UserVitalityMapper userVitalityMapper;

    @Autowired
    private UserVitalityService userVitalityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserVitalityMockMvc;

    private UserVitality userVitality;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserVitalityResource userVitalityResource = new UserVitalityResource(userVitalityService);
        this.restUserVitalityMockMvc = MockMvcBuilders.standaloneSetup(userVitalityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVitality createEntity(EntityManager em) {
        UserVitality userVitality = new UserVitality()
            .comment(DEFAULT_COMMENT)
            .range(DEFAULT_RANGE);
        return userVitality;
    }

    @Before
    public void initTest() {
        userVitality = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserVitality() throws Exception {
        int databaseSizeBeforeCreate = userVitalityRepository.findAll().size();

        // Create the UserVitality
        UserVitalityDTO userVitalityDTO = userVitalityMapper.userVitalityToUserVitalityDTO(userVitality);
        restUserVitalityMockMvc.perform(post("/api/user-vitalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userVitalityDTO)))
            .andExpect(status().isCreated());

        // Validate the UserVitality in the database
        List<UserVitality> userVitalityList = userVitalityRepository.findAll();
        assertThat(userVitalityList).hasSize(databaseSizeBeforeCreate + 1);
        UserVitality testUserVitality = userVitalityList.get(userVitalityList.size() - 1);
        assertThat(testUserVitality.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserVitality.getRange()).isEqualTo(DEFAULT_RANGE);
    }

    @Test
    @Transactional
    public void createUserVitalityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userVitalityRepository.findAll().size();

        // Create the UserVitality with an existing ID
        userVitality.setId(1L);
        UserVitalityDTO userVitalityDTO = userVitalityMapper.userVitalityToUserVitalityDTO(userVitality);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserVitalityMockMvc.perform(post("/api/user-vitalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userVitalityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserVitality> userVitalityList = userVitalityRepository.findAll();
        assertThat(userVitalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserVitalities() throws Exception {
        // Initialize the database
        userVitalityRepository.saveAndFlush(userVitality);

        // Get all the userVitalityList
        restUserVitalityMockMvc.perform(get("/api/user-vitalities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userVitality.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].range").value(hasItem(DEFAULT_RANGE)));
    }

    @Test
    @Transactional
    public void getUserVitality() throws Exception {
        // Initialize the database
        userVitalityRepository.saveAndFlush(userVitality);

        // Get the userVitality
        restUserVitalityMockMvc.perform(get("/api/user-vitalities/{id}", userVitality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userVitality.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.range").value(DEFAULT_RANGE));
    }

    @Test
    @Transactional
    public void getNonExistingUserVitality() throws Exception {
        // Get the userVitality
        restUserVitalityMockMvc.perform(get("/api/user-vitalities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserVitality() throws Exception {
        // Initialize the database
        userVitalityRepository.saveAndFlush(userVitality);
        int databaseSizeBeforeUpdate = userVitalityRepository.findAll().size();

        // Update the userVitality
        UserVitality updatedUserVitality = userVitalityRepository.findOne(userVitality.getId());
        updatedUserVitality
            .comment(UPDATED_COMMENT)
            .range(UPDATED_RANGE);
        UserVitalityDTO userVitalityDTO = userVitalityMapper.userVitalityToUserVitalityDTO(updatedUserVitality);

        restUserVitalityMockMvc.perform(put("/api/user-vitalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userVitalityDTO)))
            .andExpect(status().isOk());

        // Validate the UserVitality in the database
        List<UserVitality> userVitalityList = userVitalityRepository.findAll();
        assertThat(userVitalityList).hasSize(databaseSizeBeforeUpdate);
        UserVitality testUserVitality = userVitalityList.get(userVitalityList.size() - 1);
        assertThat(testUserVitality.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserVitality.getRange()).isEqualTo(UPDATED_RANGE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserVitality() throws Exception {
        int databaseSizeBeforeUpdate = userVitalityRepository.findAll().size();

        // Create the UserVitality
        UserVitalityDTO userVitalityDTO = userVitalityMapper.userVitalityToUserVitalityDTO(userVitality);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserVitalityMockMvc.perform(put("/api/user-vitalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userVitalityDTO)))
            .andExpect(status().isCreated());

        // Validate the UserVitality in the database
        List<UserVitality> userVitalityList = userVitalityRepository.findAll();
        assertThat(userVitalityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserVitality() throws Exception {
        // Initialize the database
        userVitalityRepository.saveAndFlush(userVitality);
        int databaseSizeBeforeDelete = userVitalityRepository.findAll().size();

        // Get the userVitality
        restUserVitalityMockMvc.perform(delete("/api/user-vitalities/{id}", userVitality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserVitality> userVitalityList = userVitalityRepository.findAll();
        assertThat(userVitalityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserVitality.class);
    }
}
