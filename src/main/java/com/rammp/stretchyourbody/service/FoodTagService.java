package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.FoodTagDTO;
import java.util.List;

/**
 * Service Interface for managing FoodTag.
 */
public interface FoodTagService {

    /**
     * Save a foodTag.
     *
     * @param foodTagDTO the entity to save
     * @return the persisted entity
     */
    FoodTagDTO save(FoodTagDTO foodTagDTO);

    /**
     *  Get all the foodTags.
     *  
     *  @return the list of entities
     */
    List<FoodTagDTO> findAll();

    /**
     *  Get the "id" foodTag.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FoodTagDTO findOne(Long id);

    /**
     *  Delete the "id" foodTag.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
