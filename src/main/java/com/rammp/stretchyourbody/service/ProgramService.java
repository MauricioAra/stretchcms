package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.ProgramDTO;
import java.util.List;

/**
 * Service Interface for managing Program.
 */
public interface ProgramService {

    /**
     * Save a program.
     *
     * @param programDTO the entity to save
     * @return the persisted entity
     */
    ProgramDTO save(ProgramDTO programDTO);

    /**
     *  Get all the programs.
     *
     *  @return the list of entities
     */
    List<ProgramDTO> findAll();

    /**
     *  Get the "id" program.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProgramDTO findOne(Long id);

    /**
     *  Delete the "id" program.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
