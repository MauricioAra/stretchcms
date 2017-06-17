package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.FoodTag;
import com.rammp.stretchyourbody.repository.FoodTagRepository;
import com.rammp.stretchyourbody.service.FoodTagService;
import com.rammp.stretchyourbody.service.dto.FoodTagDTO;
import com.rammp.stretchyourbody.service.mapper.FoodTagMapper;
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
 * Test class for the FoodTagResource REST controller.
 *
 * @see FoodTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class FoodTagResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FoodTagRepository foodTagRepository;

    @Autowired
    private FoodTagMapper foodTagMapper;

    @Autowired
    private FoodTagService foodTagService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFoodTagMockMvc;

    private FoodTag foodTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FoodTagResource foodTagResource = new FoodTagResource(foodTagService);
        this.restFoodTagMockMvc = MockMvcBuilders.standaloneSetup(foodTagResource)
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
    public static FoodTag createEntity(EntityManager em) {
        FoodTag foodTag = new FoodTag()
            .name(DEFAULT_NAME);
        return foodTag;
    }

    @Before
    public void initTest() {
        foodTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodTag() throws Exception {
        int databaseSizeBeforeCreate = foodTagRepository.findAll().size();

        // Create the FoodTag
        FoodTagDTO foodTagDTO = foodTagMapper.foodTagToFoodTagDTO(foodTag);
        restFoodTagMockMvc.perform(post("/api/food-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodTagDTO)))
            .andExpect(status().isCreated());

        // Validate the FoodTag in the database
        List<FoodTag> foodTagList = foodTagRepository.findAll();
        assertThat(foodTagList).hasSize(databaseSizeBeforeCreate + 1);
        FoodTag testFoodTag = foodTagList.get(foodTagList.size() - 1);
        assertThat(testFoodTag.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFoodTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodTagRepository.findAll().size();

        // Create the FoodTag with an existing ID
        foodTag.setId(1L);
        FoodTagDTO foodTagDTO = foodTagMapper.foodTagToFoodTagDTO(foodTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodTagMockMvc.perform(post("/api/food-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FoodTag> foodTagList = foodTagRepository.findAll();
        assertThat(foodTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFoodTags() throws Exception {
        // Initialize the database
        foodTagRepository.saveAndFlush(foodTag);

        // Get all the foodTagList
        restFoodTagMockMvc.perform(get("/api/food-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFoodTag() throws Exception {
        // Initialize the database
        foodTagRepository.saveAndFlush(foodTag);

        // Get the foodTag
        restFoodTagMockMvc.perform(get("/api/food-tags/{id}", foodTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoodTag() throws Exception {
        // Get the foodTag
        restFoodTagMockMvc.perform(get("/api/food-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodTag() throws Exception {
        // Initialize the database
        foodTagRepository.saveAndFlush(foodTag);
        int databaseSizeBeforeUpdate = foodTagRepository.findAll().size();

        // Update the foodTag
        FoodTag updatedFoodTag = foodTagRepository.findOne(foodTag.getId());
        updatedFoodTag
            .name(UPDATED_NAME);
        FoodTagDTO foodTagDTO = foodTagMapper.foodTagToFoodTagDTO(updatedFoodTag);

        restFoodTagMockMvc.perform(put("/api/food-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodTagDTO)))
            .andExpect(status().isOk());

        // Validate the FoodTag in the database
        List<FoodTag> foodTagList = foodTagRepository.findAll();
        assertThat(foodTagList).hasSize(databaseSizeBeforeUpdate);
        FoodTag testFoodTag = foodTagList.get(foodTagList.size() - 1);
        assertThat(testFoodTag.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodTag() throws Exception {
        int databaseSizeBeforeUpdate = foodTagRepository.findAll().size();

        // Create the FoodTag
        FoodTagDTO foodTagDTO = foodTagMapper.foodTagToFoodTagDTO(foodTag);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFoodTagMockMvc.perform(put("/api/food-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodTagDTO)))
            .andExpect(status().isCreated());

        // Validate the FoodTag in the database
        List<FoodTag> foodTagList = foodTagRepository.findAll();
        assertThat(foodTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFoodTag() throws Exception {
        // Initialize the database
        foodTagRepository.saveAndFlush(foodTag);
        int databaseSizeBeforeDelete = foodTagRepository.findAll().size();

        // Get the foodTag
        restFoodTagMockMvc.perform(delete("/api/food-tags/{id}", foodTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FoodTag> foodTagList = foodTagRepository.findAll();
        assertThat(foodTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodTag.class);
    }
}
