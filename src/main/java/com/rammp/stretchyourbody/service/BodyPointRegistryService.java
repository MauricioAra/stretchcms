package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.BodyPointRegistryDTO;
import java.util.List;

/**
 * Service Interface for managing BodyPointRegistry.
 */
public interface BodyPointRegistryService {

    /**
     * Save a bodyPointRegistry.
     *
     * @param bodyPointRegistryDTO the entity to save
     * @return the persisted entity
     */
    BodyPointRegistryDTO save(BodyPointRegistryDTO bodyPointRegistryDTO);

    /**
     *  Get all the bodyPointRegistries.
     *  
     *  @return the list of entities
     */
    List<BodyPointRegistryDTO> findAll();

    /**
     *  Get the "id" bodyPointRegistry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BodyPointRegistryDTO findOne(Long id);

    /**
     *  Delete the "id" bodyPointRegistry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
