package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.FoodService;
import com.rammp.stretchyourbody.domain.Food;
import com.rammp.stretchyourbody.repository.FoodRepository;
import com.rammp.stretchyourbody.service.dto.FoodDTO;
import com.rammp.stretchyourbody.service.mapper.FoodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Food.
 */
@Service
@Transactional
public class FoodServiceImpl implements FoodService{

    private final Logger log = LoggerFactory.getLogger(FoodServiceImpl.class);
    
    private final FoodRepository foodRepository;

    private final FoodMapper foodMapper;

    public FoodServiceImpl(FoodRepository foodRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }

    /**
     * Save a food.
     *
     * @param foodDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FoodDTO save(FoodDTO foodDTO) {
        log.debug("Request to save Food : {}", foodDTO);
        Food food = foodMapper.foodDTOToFood(foodDTO);
        food = foodRepository.save(food);
        FoodDTO result = foodMapper.foodToFoodDTO(food);
        return result;
    }

    /**
     *  Get all the foods.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FoodDTO> findAll() {
        log.debug("Request to get all Foods");
        List<FoodDTO> result = foodRepository.findAllWithEagerRelationships().stream()
            .map(foodMapper::foodToFoodDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one food by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FoodDTO findOne(Long id) {
        log.debug("Request to get Food : {}", id);
        Food food = foodRepository.findOneWithEagerRelationships(id);
        FoodDTO foodDTO = foodMapper.foodToFoodDTO(food);
        return foodDTO;
    }

    /**
     *  Delete the  food by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Food : {}", id);
        foodRepository.delete(id);
    }
}
