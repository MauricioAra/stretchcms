package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.Program;
import com.rammp.stretchyourbody.repository.ProgramRepository;
import com.rammp.stretchyourbody.service.ProgramService;
import com.rammp.stretchyourbody.service.dto.ProgramDTO;
import com.rammp.stretchyourbody.service.mapper.ProgramMapper;
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
 * Test class for the ProgramResource REST controller.
 *
 * @see ProgramResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class ProgramResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_INT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_FINISH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_FINISH_DATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_INTERVAL = 1;
    private static final Integer UPDATED_INTERVAL = 2;

    private static final Integer DEFAULT_CANT_REPETITION = 1;
    private static final Integer UPDATED_CANT_REPETITION = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Boolean DEFAULT_IS_DAIRY = false;
    private static final Boolean UPDATED_IS_DAIRY = true;

    private static final Boolean DEFAULT_IS_RECOMMENDED = false;
    private static final Boolean UPDATED_IS_RECOMMENDED = true;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProgramMapper programMapper;

    @Autowired
    private ProgramService programService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProgramMockMvc;

    private Program program;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProgramResource programResource = new ProgramResource(programService);
        this.restProgramMockMvc = MockMvcBuilders.standaloneSetup(programResource)
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
    public static Program createEntity(EntityManager em) {
        Program program = new Program()
            .name(DEFAULT_NAME)
            .intDate(DEFAULT_INT_DATE)
            .finishDate(DEFAULT_FINISH_DATE)
            .interval(DEFAULT_INTERVAL)
            .cantRepetition(DEFAULT_CANT_REPETITION)
            .status(DEFAULT_STATUS)
            .isDairy(DEFAULT_IS_DAIRY)
            .isRecommended(DEFAULT_IS_RECOMMENDED);
        return program;
    }

    @Before
    public void initTest() {
        program = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgram() throws Exception {
        int databaseSizeBeforeCreate = programRepository.findAll().size();

        // Create the Program
        ProgramDTO programDTO = programMapper.programToProgramDTO(program);
        restProgramMockMvc.perform(post("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programDTO)))
            .andExpect(status().isCreated());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeCreate + 1);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgram.getIntDate()).isEqualTo(DEFAULT_INT_DATE);
        assertThat(testProgram.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testProgram.getInterval()).isEqualTo(DEFAULT_INTERVAL);
        assertThat(testProgram.getCantRepetition()).isEqualTo(DEFAULT_CANT_REPETITION);
        assertThat(testProgram.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProgram.isIsDairy()).isEqualTo(DEFAULT_IS_DAIRY);
        assertThat(testProgram.isIsRecommended()).isEqualTo(DEFAULT_IS_RECOMMENDED);
    }

    @Test
    @Transactional
    public void createProgramWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programRepository.findAll().size();

        // Create the Program with an existing ID
        program.setId(1L);
        ProgramDTO programDTO = programMapper.programToProgramDTO(program);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramMockMvc.perform(post("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrograms() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList
        restProgramMockMvc.perform(get("/api/programs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(program.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].intDate").value(hasItem(DEFAULT_INT_DATE.toString())))
            .andExpect(jsonPath("$.[*].finishDate").value(hasItem(DEFAULT_FINISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].interval").value(hasItem(DEFAULT_INTERVAL)))
            .andExpect(jsonPath("$.[*].cantRepetition").value(hasItem(DEFAULT_CANT_REPETITION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].isDairy").value(hasItem(DEFAULT_IS_DAIRY.booleanValue())))
            .andExpect(jsonPath("$.[*].isRecommended").value(hasItem(DEFAULT_IS_RECOMMENDED.booleanValue())));
    }

    @Test
    @Transactional
    public void getProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(program.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.intDate").value(DEFAULT_INT_DATE.toString()))
            .andExpect(jsonPath("$.finishDate").value(DEFAULT_FINISH_DATE.toString()))
            .andExpect(jsonPath("$.interval").value(DEFAULT_INTERVAL))
            .andExpect(jsonPath("$.cantRepetition").value(DEFAULT_CANT_REPETITION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.isDairy").value(DEFAULT_IS_DAIRY.booleanValue()))
            .andExpect(jsonPath("$.isRecommended").value(DEFAULT_IS_RECOMMENDED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProgram() throws Exception {
        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);
        int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // Update the program
        Program updatedProgram = programRepository.findOne(program.getId());
        updatedProgram
            .name(UPDATED_NAME)
            .intDate(UPDATED_INT_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .interval(UPDATED_INTERVAL)
            .cantRepetition(UPDATED_CANT_REPETITION)
            .status(UPDATED_STATUS)
            .isDairy(UPDATED_IS_DAIRY)
            .isRecommended(UPDATED_IS_RECOMMENDED);
        ProgramDTO programDTO = programMapper.programToProgramDTO(updatedProgram);

        restProgramMockMvc.perform(put("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programDTO)))
            .andExpect(status().isOk());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgram.getIntDate()).isEqualTo(UPDATED_INT_DATE);
        assertThat(testProgram.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testProgram.getInterval()).isEqualTo(UPDATED_INTERVAL);
        assertThat(testProgram.getCantRepetition()).isEqualTo(UPDATED_CANT_REPETITION);
        assertThat(testProgram.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProgram.isIsDairy()).isEqualTo(UPDATED_IS_DAIRY);
        assertThat(testProgram.isIsRecommended()).isEqualTo(UPDATED_IS_RECOMMENDED);
    }

    @Test
    @Transactional
    public void updateNonExistingProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // Create the Program
        ProgramDTO programDTO = programMapper.programToProgramDTO(program);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProgramMockMvc.perform(put("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programDTO)))
            .andExpect(status().isCreated());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);
        int databaseSizeBeforeDelete = programRepository.findAll().size();

        // Get the program
        restProgramMockMvc.perform(delete("/api/programs/{id}", program.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Program.class);
    }
}
