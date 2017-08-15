package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.BodyPoint;
import com.rammp.stretchyourbody.domain.UserApp;
import com.rammp.stretchyourbody.repository.BodyPointRepository;
import com.rammp.stretchyourbody.service.BodyPointService;
import com.rammp.stretchyourbody.service.dto.BodyPointDTO;
import com.rammp.stretchyourbody.service.mapper.BodyPointMapper;
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
 * Test class for the BodyPointResource REST controller.
 *
 * @see BodyPointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class BodyPointResourceIntTest {

    private static final String DEFAULT_BODYPART = "AAAAAAAAAA";
    private static final String UPDATED_BODYPART = "BBBBBBBBBB";

    private static final Long DEFAULT_IDBODYPART = 1L;
    private static final Long UPDATED_IDBODYPART = 2L;

    @Autowired
    private BodyPointRepository bodyPointRepository;

    @Autowired
    private BodyPointMapper bodyPointMapper;

    @Autowired
    private BodyPointService bodyPointService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBodyPointMockMvc;

    private BodyPoint bodyPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BodyPointResource bodyPointResource = new BodyPointResource(bodyPointService);
        this.restBodyPointMockMvc = MockMvcBuilders.standaloneSetup(bodyPointResource)
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
    public static BodyPoint createEntity(EntityManager em) {
        BodyPoint bodyPoint = new BodyPoint()
            .bodypart(DEFAULT_BODYPART)
            .idbodypart(DEFAULT_IDBODYPART);
        // Add required entity
        UserApp userApp = UserAppResourceIntTest.createEntity(em);
        em.persist(userApp);
        em.flush();
        bodyPoint.setUserApp(userApp);
        return bodyPoint;
    }

    @Before
    public void initTest() {
        bodyPoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createBodyPoint() throws Exception {
        int databaseSizeBeforeCreate = bodyPointRepository.findAll().size();

        // Create the BodyPoint
        BodyPointDTO bodyPointDTO = bodyPointMapper.bodyPointToBodyPointDTO(bodyPoint);
        restBodyPointMockMvc.perform(post("/api/body-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointDTO)))
            .andExpect(status().isCreated());

        // Validate the BodyPoint in the database
        List<BodyPoint> bodyPointList = bodyPointRepository.findAll();
        assertThat(bodyPointList).hasSize(databaseSizeBeforeCreate + 1);
        BodyPoint testBodyPoint = bodyPointList.get(bodyPointList.size() - 1);
        assertThat(testBodyPoint.getBodypart()).isEqualTo(DEFAULT_BODYPART);
        assertThat(testBodyPoint.getIdbodypart()).isEqualTo(DEFAULT_IDBODYPART);
    }

    @Test
    @Transactional
    public void createBodyPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bodyPointRepository.findAll().size();

        // Create the BodyPoint with an existing ID
        bodyPoint.setId(1L);
        BodyPointDTO bodyPointDTO = bodyPointMapper.bodyPointToBodyPointDTO(bodyPoint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyPointMockMvc.perform(post("/api/body-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BodyPoint> bodyPointList = bodyPointRepository.findAll();
        assertThat(bodyPointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBodyPoints() throws Exception {
        // Initialize the database
        bodyPointRepository.saveAndFlush(bodyPoint);

        // Get all the bodyPointList
        restBodyPointMockMvc.perform(get("/api/body-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].bodypart").value(hasItem(DEFAULT_BODYPART.toString())))
            .andExpect(jsonPath("$.[*].idbodypart").value(hasItem(DEFAULT_IDBODYPART.intValue())));
    }

    @Test
    @Transactional
    public void getBodyPoint() throws Exception {
        // Initialize the database
        bodyPointRepository.saveAndFlush(bodyPoint);

        // Get the bodyPoint
        restBodyPointMockMvc.perform(get("/api/body-points/{id}", bodyPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bodyPoint.getId().intValue()))
            .andExpect(jsonPath("$.bodypart").value(DEFAULT_BODYPART.toString()))
            .andExpect(jsonPath("$.idbodypart").value(DEFAULT_IDBODYPART.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBodyPoint() throws Exception {
        // Get the bodyPoint
        restBodyPointMockMvc.perform(get("/api/body-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBodyPoint() throws Exception {
        // Initialize the database
        bodyPointRepository.saveAndFlush(bodyPoint);
        int databaseSizeBeforeUpdate = bodyPointRepository.findAll().size();

        // Update the bodyPoint
        BodyPoint updatedBodyPoint = bodyPointRepository.findOne(bodyPoint.getId());
        updatedBodyPoint
            .bodypart(UPDATED_BODYPART)
            .idbodypart(UPDATED_IDBODYPART);
        BodyPointDTO bodyPointDTO = bodyPointMapper.bodyPointToBodyPointDTO(updatedBodyPoint);

        restBodyPointMockMvc.perform(put("/api/body-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointDTO)))
            .andExpect(status().isOk());

        // Validate the BodyPoint in the database
        List<BodyPoint> bodyPointList = bodyPointRepository.findAll();
        assertThat(bodyPointList).hasSize(databaseSizeBeforeUpdate);
        BodyPoint testBodyPoint = bodyPointList.get(bodyPointList.size() - 1);
        assertThat(testBodyPoint.getBodypart()).isEqualTo(UPDATED_BODYPART);
        assertThat(testBodyPoint.getIdbodypart()).isEqualTo(UPDATED_IDBODYPART);
    }

    @Test
    @Transactional
    public void updateNonExistingBodyPoint() throws Exception {
        int databaseSizeBeforeUpdate = bodyPointRepository.findAll().size();

        // Create the BodyPoint
        BodyPointDTO bodyPointDTO = bodyPointMapper.bodyPointToBodyPointDTO(bodyPoint);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBodyPointMockMvc.perform(put("/api/body-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointDTO)))
            .andExpect(status().isCreated());

        // Validate the BodyPoint in the database
        List<BodyPoint> bodyPointList = bodyPointRepository.findAll();
        assertThat(bodyPointList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBodyPoint() throws Exception {
        // Initialize the database
        bodyPointRepository.saveAndFlush(bodyPoint);
        int databaseSizeBeforeDelete = bodyPointRepository.findAll().size();

        // Get the bodyPoint
        restBodyPointMockMvc.perform(delete("/api/body-points/{id}", bodyPoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BodyPoint> bodyPointList = bodyPointRepository.findAll();
        assertThat(bodyPointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyPoint.class);
    }
}
