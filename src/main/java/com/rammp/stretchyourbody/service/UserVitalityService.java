package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.UserVitalityDTO;
import java.util.List;

/**
 * Service Interface for managing UserVitality.
 */
public interface UserVitalityService {

    /**
     * Save a userVitality.
     *
     * @param userVitalityDTO the entity to save
     * @return the persisted entity
     */
    UserVitalityDTO save(UserVitalityDTO userVitalityDTO);

    /**
     *  Get all the userVitalities.
     *
     *  @return the list of entities
     */
    List<UserVitalityDTO> findAll();

    /**
     *  Get the "id" userVitality.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserVitalityDTO findOne(Long id);

    /**
     *  Delete the "id" userVitality.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<UserVitalityDTO> findAllByUserAppName(String username);
}
