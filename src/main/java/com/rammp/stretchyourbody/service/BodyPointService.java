package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.BodyPointDTO;
import java.util.List;

/**
 * Service Interface for managing BodyPoint.
 */
public interface BodyPointService {

    /**
     * Save a bodyPoint.
     *
     * @param bodyPointDTO the entity to save
     * @return the persisted entity
     */
    BodyPointDTO save(BodyPointDTO bodyPointDTO);

    /**
     *  Get all the bodyPoints.
     *  
     *  @return the list of entities
     */
    List<BodyPointDTO> findAll();

    /**
     *  Get the "id" bodyPoint.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BodyPointDTO findOne(Long id);

    /**
     *  Delete the "id" bodyPoint.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
