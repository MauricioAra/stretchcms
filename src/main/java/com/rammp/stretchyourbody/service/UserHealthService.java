package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.UserHealthDTO;
import java.util.List;

/**
 * Service Interface for managing UserHealth.
 */
public interface UserHealthService {

    /**
     * Save a userHealth.
     *
     * @param userHealthDTO the entity to save
     * @return the persisted entity
     */
    UserHealthDTO save(UserHealthDTO userHealthDTO);

    /**
     *  Get all the userHealths.
     *  
     *  @return the list of entities
     */
    List<UserHealthDTO> findAll();
    /**
     *  Get all the UserHealthDTO where UserApp is null.
     *
     *  @return the list of entities
     */
    List<UserHealthDTO> findAllWhereUserAppIsNull();

    /**
     *  Get the "id" userHealth.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserHealthDTO findOne(Long id);

    /**
     *  Delete the "id" userHealth.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
