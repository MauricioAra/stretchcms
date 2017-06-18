package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.StretchCmsApp;

import com.rammp.stretchyourbody.domain.Food;
import com.rammp.stretchyourbody.repository.FoodRepository;
import com.rammp.stretchyourbody.service.FoodService;
import com.rammp.stretchyourbody.service.dto.FoodDTO;
import com.rammp.stretchyourbody.service.mapper.FoodMapper;
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
 * Test class for the FoodResource REST controller.
 *
 * @see FoodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StretchCmsApp.class)
public class FoodResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Boolean DEFAULT_IS_RECOMMENDED = false;
    private static final Boolean UPDATED_IS_RECOMMENDED = true;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private FoodService foodService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFoodMockMvc;

    private Food food;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FoodResource foodResource = new FoodResource(foodService);
        this.restFoodMockMvc = MockMvcBuilders.standaloneSetup(foodResource)
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
    public static Food createEntity(EntityManager em) {
        Food food = new Food()
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .isRecommended(DEFAULT_IS_RECOMMENDED);
        return food;
    }

    @Before
    public void initTest() {
        food = createEntity(em);
    }

    @Test
    @Transactional
    public void createFood() throws Exception {
        int databaseSizeBeforeCreate = foodRepository.findAll().size();

        // Create the Food
        FoodDTO foodDTO = foodMapper.foodToFoodDTO(food);
        restFoodMockMvc.perform(post("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodDTO)))
            .andExpect(status().isCreated());

        // Validate the Food in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeCreate + 1);
        Food testFood = foodList.get(foodList.size() - 1);
        assertThat(testFood.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFood.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testFood.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFood.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFood.isIsRecommended()).isEqualTo(DEFAULT_IS_RECOMMENDED);
    }

    @Test
    @Transactional
    public void createFoodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodRepository.findAll().size();

        // Create the Food with an existing ID
        food.setId(1L);
        FoodDTO foodDTO = foodMapper.foodToFoodDTO(food);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodMockMvc.perform(post("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFoods() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);

        // Get all the foodList
        restFoodMockMvc.perform(get("/api/foods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(food.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].isRecommended").value(hasItem(DEFAULT_IS_RECOMMENDED.booleanValue())));
    }

    @Test
    @Transactional
    public void getFood() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);

        // Get the food
        restFoodMockMvc.perform(get("/api/foods/{id}", food.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(food.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.isRecommended").value(DEFAULT_IS_RECOMMENDED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFood() throws Exception {
        // Get the food
        restFoodMockMvc.perform(get("/api/foods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFood() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);
        int databaseSizeBeforeUpdate = foodRepository.findAll().size();

        // Update the food
        Food updatedFood = foodRepository.findOne(food.getId());
        updatedFood
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .isRecommended(UPDATED_IS_RECOMMENDED);
        FoodDTO foodDTO = foodMapper.foodToFoodDTO(updatedFood);

        restFoodMockMvc.perform(put("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodDTO)))
            .andExpect(status().isOk());

        // Validate the Food in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeUpdate);
        Food testFood = foodList.get(foodList.size() - 1);
        assertThat(testFood.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFood.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testFood.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFood.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFood.isIsRecommended()).isEqualTo(UPDATED_IS_RECOMMENDED);
    }

    @Test
    @Transactional
    public void updateNonExistingFood() throws Exception {
        int databaseSizeBeforeUpdate = foodRepository.findAll().size();

        // Create the Food
        FoodDTO foodDTO = foodMapper.foodToFoodDTO(food);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFoodMockMvc.perform(put("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodDTO)))
            .andExpect(status().isCreated());

        // Validate the Food in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFood() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);
        int databaseSizeBeforeDelete = foodRepository.findAll().size();

        // Get the food
        restFoodMockMvc.perform(delete("/api/foods/{id}", food.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Food.class);
    }
}
