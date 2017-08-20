package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.ExerciseDTO;
import java.util.List;

/**
 * Service Interface for managing Exercise.
 */
public interface ExerciseService {

    /**
     * Save a exercise.
     *
     * @param exerciseDTO the entity to save
     * @return the persisted entity
     */
    ExerciseDTO save(ExerciseDTO exerciseDTO);

    /**
     *  Get all the exercises.
     *
     *  @return the list of entities
     */
    List<ExerciseDTO> findAll();

    /**
     *  Get the "id" exercise.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExerciseDTO findOne(Long id);

    /**
     *  Delete the "id" exercise.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<ExerciseDTO> findByBodyPart(Long id);
}
