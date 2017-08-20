package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.ExerciseService;
import com.rammp.stretchyourbody.domain.Exercise;
import com.rammp.stretchyourbody.repository.ExerciseRepository;
import com.rammp.stretchyourbody.service.dto.ExerciseDTO;
import com.rammp.stretchyourbody.service.mapper.ExerciseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Exercise.
 */
@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService{

    private final Logger log = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    /**
     * Save a exercise.
     *
     * @param exerciseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExerciseDTO save(ExerciseDTO exerciseDTO) {
        log.debug("Request to save Exercise : {}", exerciseDTO);
        Exercise exercise = exerciseMapper.exerciseDTOToExercise(exerciseDTO);
        exercise = exerciseRepository.save(exercise);
        ExerciseDTO result = exerciseMapper.exerciseToExerciseDTO(exercise);
        return result;
    }

    /**
     *  Get all the exercises.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExerciseDTO> findAll() {
        log.debug("Request to get all Exercises");
        List<ExerciseDTO> result = exerciseRepository.findAll().stream()
            .map(exerciseMapper::exerciseToExerciseDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one exercise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExerciseDTO findOne(Long id) {
        log.debug("Request to get Exercise : {}", id);
        Exercise exercise = exerciseRepository.findOne(id);
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);
        return exerciseDTO;
    }

    /**
     *  Delete the  exercise by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exercise : {}", id);
        exerciseRepository.delete(id);
    }

    public List<ExerciseDTO> findByBodyPart(Long id){

        List<ExerciseDTO> result = exerciseRepository.findByBodyPartId(id).stream()
            .map(exerciseMapper::exerciseToExerciseDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }
}
