package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.UserAppDTO;
import java.util.List;

/**
 * Service Interface for managing UserApp.
 */
public interface UserAppService {

    /**
     * Save a userApp.
     *
     * @param userAppDTO the entity to save
     * @return the persisted entity
     */
    UserAppDTO save(UserAppDTO userAppDTO);

    /**
     *  Get all the userApps.
     *  
     *  @return the list of entities
     */
    List<UserAppDTO> findAll();

    /**
     *  Get the "id" userApp.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserAppDTO findOne(Long id);

    /**
     *  Delete the "id" userApp.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
