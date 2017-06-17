package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.UserHealth;
import com.rammp.stretchyourbody.repository.UserHealthRepository;
import com.rammp.stretchyourbody.service.UserHealthService;
import com.rammp.stretchyourbody.service.dto.UserHealthDTO;
import com.rammp.stretchyourbody.service.mapper.UserHealthMapper;
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
 * Test class for the UserHealthResource REST controller.
 *
 * @see UserHealthResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class UserHealthResourceIntTest {

    private static final Integer DEFAULT_WORK_HOURS = 1;
    private static final Integer UPDATED_WORK_HOURS = 2;

    private static final Boolean DEFAULT_DOES_WORK_OUT = false;
    private static final Boolean UPDATED_DOES_WORK_OUT = true;

    private static final Boolean DEFAULT_IS_SMOKER = false;
    private static final Boolean UPDATED_IS_SMOKER = true;

    private static final Boolean DEFAULT_IS_HEALTH_FOOD = false;
    private static final Boolean UPDATED_IS_HEALTH_FOOD = true;

    @Autowired
    private UserHealthRepository userHealthRepository;

    @Autowired
    private UserHealthMapper userHealthMapper;

    @Autowired
    private UserHealthService userHealthService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserHealthMockMvc;

    private UserHealth userHealth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserHealthResource userHealthResource = new UserHealthResource(userHealthService);
        this.restUserHealthMockMvc = MockMvcBuilders.standaloneSetup(userHealthResource)
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
    public static UserHealth createEntity(EntityManager em) {
        UserHealth userHealth = new UserHealth()
            .workHours(DEFAULT_WORK_HOURS)
            .doesWorkOut(DEFAULT_DOES_WORK_OUT)
            .isSmoker(DEFAULT_IS_SMOKER)
            .isHealthFood(DEFAULT_IS_HEALTH_FOOD);
        return userHealth;
    }

    @Before
    public void initTest() {
        userHealth = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserHealth() throws Exception {
        int databaseSizeBeforeCreate = userHealthRepository.findAll().size();

        // Create the UserHealth
        UserHealthDTO userHealthDTO = userHealthMapper.userHealthToUserHealthDTO(userHealth);
        restUserHealthMockMvc.perform(post("/api/user-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userHealthDTO)))
            .andExpect(status().isCreated());

        // Validate the UserHealth in the database
        List<UserHealth> userHealthList = userHealthRepository.findAll();
        assertThat(userHealthList).hasSize(databaseSizeBeforeCreate + 1);
        UserHealth testUserHealth = userHealthList.get(userHealthList.size() - 1);
        assertThat(testUserHealth.getWorkHours()).isEqualTo(DEFAULT_WORK_HOURS);
        assertThat(testUserHealth.isDoesWorkOut()).isEqualTo(DEFAULT_DOES_WORK_OUT);
        assertThat(testUserHealth.isIsSmoker()).isEqualTo(DEFAULT_IS_SMOKER);
        assertThat(testUserHealth.isIsHealthFood()).isEqualTo(DEFAULT_IS_HEALTH_FOOD);
    }

    @Test
    @Transactional
    public void createUserHealthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userHealthRepository.findAll().size();

        // Create the UserHealth with an existing ID
        userHealth.setId(1L);
        UserHealthDTO userHealthDTO = userHealthMapper.userHealthToUserHealthDTO(userHealth);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserHealthMockMvc.perform(post("/api/user-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userHealthDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserHealth> userHealthList = userHealthRepository.findAll();
        assertThat(userHealthList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserHealths() throws Exception {
        // Initialize the database
        userHealthRepository.saveAndFlush(userHealth);

        // Get all the userHealthList
        restUserHealthMockMvc.perform(get("/api/user-healths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userHealth.getId().intValue())))
            .andExpect(jsonPath("$.[*].workHours").value(hasItem(DEFAULT_WORK_HOURS)))
            .andExpect(jsonPath("$.[*].doesWorkOut").value(hasItem(DEFAULT_DOES_WORK_OUT.booleanValue())))
            .andExpect(jsonPath("$.[*].isSmoker").value(hasItem(DEFAULT_IS_SMOKER.booleanValue())))
            .andExpect(jsonPath("$.[*].isHealthFood").value(hasItem(DEFAULT_IS_HEALTH_FOOD.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserHealth() throws Exception {
        // Initialize the database
        userHealthRepository.saveAndFlush(userHealth);

        // Get the userHealth
        restUserHealthMockMvc.perform(get("/api/user-healths/{id}", userHealth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userHealth.getId().intValue()))
            .andExpect(jsonPath("$.workHours").value(DEFAULT_WORK_HOURS))
            .andExpect(jsonPath("$.doesWorkOut").value(DEFAULT_DOES_WORK_OUT.booleanValue()))
            .andExpect(jsonPath("$.isSmoker").value(DEFAULT_IS_SMOKER.booleanValue()))
            .andExpect(jsonPath("$.isHealthFood").value(DEFAULT_IS_HEALTH_FOOD.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserHealth() throws Exception {
        // Get the userHealth
        restUserHealthMockMvc.perform(get("/api/user-healths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserHealth() throws Exception {
        // Initialize the database
        userHealthRepository.saveAndFlush(userHealth);
        int databaseSizeBeforeUpdate = userHealthRepository.findAll().size();

        // Update the userHealth
        UserHealth updatedUserHealth = userHealthRepository.findOne(userHealth.getId());
        updatedUserHealth
            .workHours(UPDATED_WORK_HOURS)
            .doesWorkOut(UPDATED_DOES_WORK_OUT)
            .isSmoker(UPDATED_IS_SMOKER)
            .isHealthFood(UPDATED_IS_HEALTH_FOOD);
        UserHealthDTO userHealthDTO = userHealthMapper.userHealthToUserHealthDTO(updatedUserHealth);

        restUserHealthMockMvc.perform(put("/api/user-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userHealthDTO)))
            .andExpect(status().isOk());

        // Validate the UserHealth in the database
        List<UserHealth> userHealthList = userHealthRepository.findAll();
        assertThat(userHealthList).hasSize(databaseSizeBeforeUpdate);
        UserHealth testUserHealth = userHealthList.get(userHealthList.size() - 1);
        assertThat(testUserHealth.getWorkHours()).isEqualTo(UPDATED_WORK_HOURS);
        assertThat(testUserHealth.isDoesWorkOut()).isEqualTo(UPDATED_DOES_WORK_OUT);
        assertThat(testUserHealth.isIsSmoker()).isEqualTo(UPDATED_IS_SMOKER);
        assertThat(testUserHealth.isIsHealthFood()).isEqualTo(UPDATED_IS_HEALTH_FOOD);
    }

    @Test
    @Transactional
    public void updateNonExistingUserHealth() throws Exception {
        int databaseSizeBeforeUpdate = userHealthRepository.findAll().size();

        // Create the UserHealth
        UserHealthDTO userHealthDTO = userHealthMapper.userHealthToUserHealthDTO(userHealth);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserHealthMockMvc.perform(put("/api/user-healths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userHealthDTO)))
            .andExpect(status().isCreated());

        // Validate the UserHealth in the database
        List<UserHealth> userHealthList = userHealthRepository.findAll();
        assertThat(userHealthList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserHealth() throws Exception {
        // Initialize the database
        userHealthRepository.saveAndFlush(userHealth);
        int databaseSizeBeforeDelete = userHealthRepository.findAll().size();

        // Get the userHealth
        restUserHealthMockMvc.perform(delete("/api/user-healths/{id}", userHealth.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserHealth> userHealthList = userHealthRepository.findAll();
        assertThat(userHealthList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserHealth.class);
    }
}
