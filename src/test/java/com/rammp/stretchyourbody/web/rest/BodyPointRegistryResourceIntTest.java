package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.BodyPointRegistry;
import com.rammp.stretchyourbody.domain.BodyPoint;
import com.rammp.stretchyourbody.repository.BodyPointRegistryRepository;
import com.rammp.stretchyourbody.service.BodyPointRegistryService;
import com.rammp.stretchyourbody.service.dto.BodyPointRegistryDTO;
import com.rammp.stretchyourbody.service.mapper.BodyPointRegistryMapper;
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
 * Test class for the BodyPointRegistryResource REST controller.
 *
 * @see BodyPointRegistryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class BodyPointRegistryResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private BodyPointRegistryRepository bodyPointRegistryRepository;

    @Autowired
    private BodyPointRegistryMapper bodyPointRegistryMapper;

    @Autowired
    private BodyPointRegistryService bodyPointRegistryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBodyPointRegistryMockMvc;

    private BodyPointRegistry bodyPointRegistry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BodyPointRegistryResource bodyPointRegistryResource = new BodyPointRegistryResource(bodyPointRegistryService);
        this.restBodyPointRegistryMockMvc = MockMvcBuilders.standaloneSetup(bodyPointRegistryResource)
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
    public static BodyPointRegistry createEntity(EntityManager em) {
        BodyPointRegistry bodyPointRegistry = new BodyPointRegistry()
            .type(DEFAULT_TYPE)
            .comment(DEFAULT_COMMENT);
        // Add required entity
        BodyPoint bodyPoint = BodyPointResourceIntTest.createEntity(em);
        em.persist(bodyPoint);
        em.flush();
        bodyPointRegistry.setBodyPoint(bodyPoint);
        return bodyPointRegistry;
    }

    @Before
    public void initTest() {
        bodyPointRegistry = createEntity(em);
    }

    @Test
    @Transactional
    public void createBodyPointRegistry() throws Exception {
        int databaseSizeBeforeCreate = bodyPointRegistryRepository.findAll().size();

        // Create the BodyPointRegistry
        BodyPointRegistryDTO bodyPointRegistryDTO = bodyPointRegistryMapper.bodyPointRegistryToBodyPointRegistryDTO(bodyPointRegistry);
        restBodyPointRegistryMockMvc.perform(post("/api/body-point-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointRegistryDTO)))
            .andExpect(status().isCreated());

        // Validate the BodyPointRegistry in the database
        List<BodyPointRegistry> bodyPointRegistryList = bodyPointRegistryRepository.findAll();
        assertThat(bodyPointRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        BodyPointRegistry testBodyPointRegistry = bodyPointRegistryList.get(bodyPointRegistryList.size() - 1);
        assertThat(testBodyPointRegistry.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBodyPointRegistry.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createBodyPointRegistryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bodyPointRegistryRepository.findAll().size();

        // Create the BodyPointRegistry with an existing ID
        bodyPointRegistry.setId(1L);
        BodyPointRegistryDTO bodyPointRegistryDTO = bodyPointRegistryMapper.bodyPointRegistryToBodyPointRegistryDTO(bodyPointRegistry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyPointRegistryMockMvc.perform(post("/api/body-point-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointRegistryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BodyPointRegistry> bodyPointRegistryList = bodyPointRegistryRepository.findAll();
        assertThat(bodyPointRegistryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBodyPointRegistries() throws Exception {
        // Initialize the database
        bodyPointRegistryRepository.saveAndFlush(bodyPointRegistry);

        // Get all the bodyPointRegistryList
        restBodyPointRegistryMockMvc.perform(get("/api/body-point-registries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyPointRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getBodyPointRegistry() throws Exception {
        // Initialize the database
        bodyPointRegistryRepository.saveAndFlush(bodyPointRegistry);

        // Get the bodyPointRegistry
        restBodyPointRegistryMockMvc.perform(get("/api/body-point-registries/{id}", bodyPointRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bodyPointRegistry.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBodyPointRegistry() throws Exception {
        // Get the bodyPointRegistry
        restBodyPointRegistryMockMvc.perform(get("/api/body-point-registries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBodyPointRegistry() throws Exception {
        // Initialize the database
        bodyPointRegistryRepository.saveAndFlush(bodyPointRegistry);
        int databaseSizeBeforeUpdate = bodyPointRegistryRepository.findAll().size();

        // Update the bodyPointRegistry
        BodyPointRegistry updatedBodyPointRegistry = bodyPointRegistryRepository.findOne(bodyPointRegistry.getId());
        updatedBodyPointRegistry
            .type(UPDATED_TYPE)
            .comment(UPDATED_COMMENT);
        BodyPointRegistryDTO bodyPointRegistryDTO = bodyPointRegistryMapper.bodyPointRegistryToBodyPointRegistryDTO(updatedBodyPointRegistry);

        restBodyPointRegistryMockMvc.perform(put("/api/body-point-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointRegistryDTO)))
            .andExpect(status().isOk());

        // Validate the BodyPointRegistry in the database
        List<BodyPointRegistry> bodyPointRegistryList = bodyPointRegistryRepository.findAll();
        assertThat(bodyPointRegistryList).hasSize(databaseSizeBeforeUpdate);
        BodyPointRegistry testBodyPointRegistry = bodyPointRegistryList.get(bodyPointRegistryList.size() - 1);
        assertThat(testBodyPointRegistry.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBodyPointRegistry.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBodyPointRegistry() throws Exception {
        int databaseSizeBeforeUpdate = bodyPointRegistryRepository.findAll().size();

        // Create the BodyPointRegistry
        BodyPointRegistryDTO bodyPointRegistryDTO = bodyPointRegistryMapper.bodyPointRegistryToBodyPointRegistryDTO(bodyPointRegistry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBodyPointRegistryMockMvc.perform(put("/api/body-point-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyPointRegistryDTO)))
            .andExpect(status().isCreated());

        // Validate the BodyPointRegistry in the database
        List<BodyPointRegistry> bodyPointRegistryList = bodyPointRegistryRepository.findAll();
        assertThat(bodyPointRegistryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBodyPointRegistry() throws Exception {
        // Initialize the database
        bodyPointRegistryRepository.saveAndFlush(bodyPointRegistry);
        int databaseSizeBeforeDelete = bodyPointRegistryRepository.findAll().size();

        // Get the bodyPointRegistry
        restBodyPointRegistryMockMvc.perform(delete("/api/body-point-registries/{id}", bodyPointRegistry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BodyPointRegistry> bodyPointRegistryList = bodyPointRegistryRepository.findAll();
        assertThat(bodyPointRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyPointRegistry.class);
    }
}
