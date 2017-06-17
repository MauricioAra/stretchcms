package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.ProgramFeedBack;
import com.rammp.stretchyourbody.repository.ProgramFeedBackRepository;
import com.rammp.stretchyourbody.service.ProgramFeedBackService;
import com.rammp.stretchyourbody.service.dto.ProgramFeedBackDTO;
import com.rammp.stretchyourbody.service.mapper.ProgramFeedBackMapper;
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
 * Test class for the ProgramFeedBackResource REST controller.
 *
 * @see ProgramFeedBackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class ProgramFeedBackResourceIntTest {

    private static final Boolean DEFAULT_IS_USEFUL = false;
    private static final Boolean UPDATED_IS_USEFUL = true;

    private static final Boolean DEFAULT_IS_HELP_PAIN = false;
    private static final Boolean UPDATED_IS_HELP_PAIN = true;

    @Autowired
    private ProgramFeedBackRepository programFeedBackRepository;

    @Autowired
    private ProgramFeedBackMapper programFeedBackMapper;

    @Autowired
    private ProgramFeedBackService programFeedBackService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProgramFeedBackMockMvc;

    private ProgramFeedBack programFeedBack;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProgramFeedBackResource programFeedBackResource = new ProgramFeedBackResource(programFeedBackService);
        this.restProgramFeedBackMockMvc = MockMvcBuilders.standaloneSetup(programFeedBackResource)
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
    public static ProgramFeedBack createEntity(EntityManager em) {
        ProgramFeedBack programFeedBack = new ProgramFeedBack()
            .isUseful(DEFAULT_IS_USEFUL)
            .isHelpPain(DEFAULT_IS_HELP_PAIN);
        return programFeedBack;
    }

    @Before
    public void initTest() {
        programFeedBack = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgramFeedBack() throws Exception {
        int databaseSizeBeforeCreate = programFeedBackRepository.findAll().size();

        // Create the ProgramFeedBack
        ProgramFeedBackDTO programFeedBackDTO = programFeedBackMapper.programFeedBackToProgramFeedBackDTO(programFeedBack);
        restProgramFeedBackMockMvc.perform(post("/api/program-feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programFeedBackDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgramFeedBack in the database
        List<ProgramFeedBack> programFeedBackList = programFeedBackRepository.findAll();
        assertThat(programFeedBackList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramFeedBack testProgramFeedBack = programFeedBackList.get(programFeedBackList.size() - 1);
        assertThat(testProgramFeedBack.isIsUseful()).isEqualTo(DEFAULT_IS_USEFUL);
        assertThat(testProgramFeedBack.isIsHelpPain()).isEqualTo(DEFAULT_IS_HELP_PAIN);
    }

    @Test
    @Transactional
    public void createProgramFeedBackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programFeedBackRepository.findAll().size();

        // Create the ProgramFeedBack with an existing ID
        programFeedBack.setId(1L);
        ProgramFeedBackDTO programFeedBackDTO = programFeedBackMapper.programFeedBackToProgramFeedBackDTO(programFeedBack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramFeedBackMockMvc.perform(post("/api/program-feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programFeedBackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProgramFeedBack> programFeedBackList = programFeedBackRepository.findAll();
        assertThat(programFeedBackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProgramFeedBacks() throws Exception {
        // Initialize the database
        programFeedBackRepository.saveAndFlush(programFeedBack);

        // Get all the programFeedBackList
        restProgramFeedBackMockMvc.perform(get("/api/program-feed-backs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programFeedBack.getId().intValue())))
            .andExpect(jsonPath("$.[*].isUseful").value(hasItem(DEFAULT_IS_USEFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].isHelpPain").value(hasItem(DEFAULT_IS_HELP_PAIN.booleanValue())));
    }

    @Test
    @Transactional
    public void getProgramFeedBack() throws Exception {
        // Initialize the database
        programFeedBackRepository.saveAndFlush(programFeedBack);

        // Get the programFeedBack
        restProgramFeedBackMockMvc.perform(get("/api/program-feed-backs/{id}", programFeedBack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programFeedBack.getId().intValue()))
            .andExpect(jsonPath("$.isUseful").value(DEFAULT_IS_USEFUL.booleanValue()))
            .andExpect(jsonPath("$.isHelpPain").value(DEFAULT_IS_HELP_PAIN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProgramFeedBack() throws Exception {
        // Get the programFeedBack
        restProgramFeedBackMockMvc.perform(get("/api/program-feed-backs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgramFeedBack() throws Exception {
        // Initialize the database
        programFeedBackRepository.saveAndFlush(programFeedBack);
        int databaseSizeBeforeUpdate = programFeedBackRepository.findAll().size();

        // Update the programFeedBack
        ProgramFeedBack updatedProgramFeedBack = programFeedBackRepository.findOne(programFeedBack.getId());
        updatedProgramFeedBack
            .isUseful(UPDATED_IS_USEFUL)
            .isHelpPain(UPDATED_IS_HELP_PAIN);
        ProgramFeedBackDTO programFeedBackDTO = programFeedBackMapper.programFeedBackToProgramFeedBackDTO(updatedProgramFeedBack);

        restProgramFeedBackMockMvc.perform(put("/api/program-feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programFeedBackDTO)))
            .andExpect(status().isOk());

        // Validate the ProgramFeedBack in the database
        List<ProgramFeedBack> programFeedBackList = programFeedBackRepository.findAll();
        assertThat(programFeedBackList).hasSize(databaseSizeBeforeUpdate);
        ProgramFeedBack testProgramFeedBack = programFeedBackList.get(programFeedBackList.size() - 1);
        assertThat(testProgramFeedBack.isIsUseful()).isEqualTo(UPDATED_IS_USEFUL);
        assertThat(testProgramFeedBack.isIsHelpPain()).isEqualTo(UPDATED_IS_HELP_PAIN);
    }

    @Test
    @Transactional
    public void updateNonExistingProgramFeedBack() throws Exception {
        int databaseSizeBeforeUpdate = programFeedBackRepository.findAll().size();

        // Create the ProgramFeedBack
        ProgramFeedBackDTO programFeedBackDTO = programFeedBackMapper.programFeedBackToProgramFeedBackDTO(programFeedBack);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProgramFeedBackMockMvc.perform(put("/api/program-feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programFeedBackDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgramFeedBack in the database
        List<ProgramFeedBack> programFeedBackList = programFeedBackRepository.findAll();
        assertThat(programFeedBackList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProgramFeedBack() throws Exception {
        // Initialize the database
        programFeedBackRepository.saveAndFlush(programFeedBack);
        int databaseSizeBeforeDelete = programFeedBackRepository.findAll().size();

        // Get the programFeedBack
        restProgramFeedBackMockMvc.perform(delete("/api/program-feed-backs/{id}", programFeedBack.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProgramFeedBack> programFeedBackList = programFeedBackRepository.findAll();
        assertThat(programFeedBackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramFeedBack.class);
    }
}
