package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.ProgramFeedBackDTO;
import java.util.List;

/**
 * Service Interface for managing ProgramFeedBack.
 */
public interface ProgramFeedBackService {

    /**
     * Save a programFeedBack.
     *
     * @param programFeedBackDTO the entity to save
     * @return the persisted entity
     */
    ProgramFeedBackDTO save(ProgramFeedBackDTO programFeedBackDTO);

    /**
     *  Get all the programFeedBacks.
     *  
     *  @return the list of entities
     */
    List<ProgramFeedBackDTO> findAll();

    /**
     *  Get the "id" programFeedBack.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProgramFeedBackDTO findOne(Long id);

    /**
     *  Delete the "id" programFeedBack.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
