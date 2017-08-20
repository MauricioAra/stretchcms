package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.Exercise;
import com.rammp.stretchyourbody.repository.ExerciseRepository;
import com.rammp.stretchyourbody.service.ExerciseService;
import com.rammp.stretchyourbody.service.dto.ExerciseDTO;
import com.rammp.stretchyourbody.service.mapper.ExerciseMapper;
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
 * Test class for the ExerciseResource REST controller.
 *
 * @see ExerciseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class ExerciseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final Integer DEFAULT_REPETITION = 1;
    private static final Integer UPDATED_REPETITION = 2;

    private static final String DEFAULT_DIFFICULTY = "AAAAAAAAAA";
    private static final String UPDATED_DIFFICULTY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CALIFICATION = 1;
    private static final Integer UPDATED_CALIFICATION = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Boolean DEFAULT_IS_RECOMMENDED = false;
    private static final Boolean UPDATED_IS_RECOMMENDED = true;

    private static final Integer DEFAULT_TOTALCALIFICATION = 1;
    private static final Integer UPDATED_TOTALCALIFICATION = 2;

    private static final Integer DEFAULT_COUNTVOTES = 1;
    private static final Integer UPDATED_COUNTVOTES = 2;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExerciseMockMvc;

    private Exercise exercise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseResource exerciseResource = new ExerciseResource(exerciseService);
        this.restExerciseMockMvc = MockMvcBuilders.standaloneSetup(exerciseResource)
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
    public static Exercise createEntity(EntityManager em) {
        Exercise exercise = new Exercise()
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .time(DEFAULT_TIME)
            .repetition(DEFAULT_REPETITION)
            .difficulty(DEFAULT_DIFFICULTY)
            .calification(DEFAULT_CALIFICATION)
            .status(DEFAULT_STATUS)
            .isRecommended(DEFAULT_IS_RECOMMENDED)
            .totalcalification(DEFAULT_TOTALCALIFICATION)
            .countvotes(DEFAULT_COUNTVOTES);
        return exercise;
    }

    @Before
    public void initTest() {
        exercise = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercise() throws Exception {
        int databaseSizeBeforeCreate = exerciseRepository.findAll().size();

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);
        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isCreated());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeCreate + 1);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExercise.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testExercise.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testExercise.getRepetition()).isEqualTo(DEFAULT_REPETITION);
        assertThat(testExercise.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testExercise.getCalification()).isEqualTo(DEFAULT_CALIFICATION);
        assertThat(testExercise.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExercise.isIsRecommended()).isEqualTo(DEFAULT_IS_RECOMMENDED);
        assertThat(testExercise.getTotalcalification()).isEqualTo(DEFAULT_TOTALCALIFICATION);
        assertThat(testExercise.getCountvotes()).isEqualTo(DEFAULT_COUNTVOTES);
    }

    @Test
    @Transactional
    public void createExerciseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseRepository.findAll().size();

        // Create the Exercise with an existing ID
        exercise.setId(1L);
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setName(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setImage(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setTime(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepetitionIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setRepetition(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDifficultyIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setDifficulty(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCalificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRepository.findAll().size();
        // set the field null
        exercise.setCalification(null);

        // Create the Exercise, which fails.
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        restExerciseMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isBadRequest());

        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExercises() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get all the exerciseList
        restExerciseMockMvc.perform(get("/api/exercises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercise.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].repetition").value(hasItem(DEFAULT_REPETITION)))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].calification").value(hasItem(DEFAULT_CALIFICATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].isRecommended").value(hasItem(DEFAULT_IS_RECOMMENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].totalcalification").value(hasItem(DEFAULT_TOTALCALIFICATION)))
            .andExpect(jsonPath("$.[*].countvotes").value(hasItem(DEFAULT_COUNTVOTES)));
    }

    @Test
    @Transactional
    public void getExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);

        // Get the exercise
        restExerciseMockMvc.perform(get("/api/exercises/{id}", exercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exercise.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.repetition").value(DEFAULT_REPETITION))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.calification").value(DEFAULT_CALIFICATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.isRecommended").value(DEFAULT_IS_RECOMMENDED.booleanValue()))
            .andExpect(jsonPath("$.totalcalification").value(DEFAULT_TOTALCALIFICATION))
            .andExpect(jsonPath("$.countvotes").value(DEFAULT_COUNTVOTES));
    }

    @Test
    @Transactional
    public void getNonExistingExercise() throws Exception {
        // Get the exercise
        restExerciseMockMvc.perform(get("/api/exercises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Update the exercise
        Exercise updatedExercise = exerciseRepository.findOne(exercise.getId());
        updatedExercise
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .time(UPDATED_TIME)
            .repetition(UPDATED_REPETITION)
            .difficulty(UPDATED_DIFFICULTY)
            .calification(UPDATED_CALIFICATION)
            .status(UPDATED_STATUS)
            .isRecommended(UPDATED_IS_RECOMMENDED)
            .totalcalification(UPDATED_TOTALCALIFICATION)
            .countvotes(UPDATED_COUNTVOTES);
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(updatedExercise);

        restExerciseMockMvc.perform(put("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isOk());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate);
        Exercise testExercise = exerciseList.get(exerciseList.size() - 1);
        assertThat(testExercise.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExercise.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testExercise.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testExercise.getRepetition()).isEqualTo(UPDATED_REPETITION);
        assertThat(testExercise.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testExercise.getCalification()).isEqualTo(UPDATED_CALIFICATION);
        assertThat(testExercise.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExercise.isIsRecommended()).isEqualTo(UPDATED_IS_RECOMMENDED);
        assertThat(testExercise.getTotalcalification()).isEqualTo(UPDATED_TOTALCALIFICATION);
        assertThat(testExercise.getCountvotes()).isEqualTo(UPDATED_COUNTVOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingExercise() throws Exception {
        int databaseSizeBeforeUpdate = exerciseRepository.findAll().size();

        // Create the Exercise
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExerciseMockMvc.perform(put("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciseDTO)))
            .andExpect(status().isCreated());

        // Validate the Exercise in the database
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExercise() throws Exception {
        // Initialize the database
        exerciseRepository.saveAndFlush(exercise);
        int databaseSizeBeforeDelete = exerciseRepository.findAll().size();

        // Get the exercise
        restExerciseMockMvc.perform(delete("/api/exercises/{id}", exercise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Exercise> exerciseList = exerciseRepository.findAll();
        assertThat(exerciseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exercise.class);
    }
}
