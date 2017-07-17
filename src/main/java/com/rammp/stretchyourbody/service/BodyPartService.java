package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.BodyPartDTO;
import java.util.List;

/**
 * Service Interface for managing BodyPart.
 */
public interface BodyPartService {

    /**
     * Save a bodyPart.
     *
     * @param bodyPartDTO the entity to save
     * @return the persisted entity
     */
    BodyPartDTO save(BodyPartDTO bodyPartDTO);

    /**
     *  Get all the bodyParts.
     *
     *  @return the list of entities
     */
    List<BodyPartDTO> findAll();

    /**
     *  Get the "id" bodyPart.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BodyPartDTO findOne(Long id);

    /**
     *  Delete the "id" bodyPart.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<BodyPartDTO> findBySubcategory(Long id);


}
