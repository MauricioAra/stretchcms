package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.FoodDTO;
import java.util.List;

/**
 * Service Interface for managing Food.
 */
public interface FoodService {

    /**
     * Save a food.
     *
     * @param foodDTO the entity to save
     * @return the persisted entity
     */
    FoodDTO save(FoodDTO foodDTO);

    /**
     *  Get all the foods.
     *
     *  @return the list of entities
     */
    List<FoodDTO> findAll();

    /**
     *  Get the "id" food.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FoodDTO findOne(Long id);

    /**
     *  Delete the "id" food.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<FoodDTO> findFoodRecommended();
}
