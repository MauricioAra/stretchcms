package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.ExerciseService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.ExerciseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Exercise.
 */
@RestController
@RequestMapping("/api")
public class ExerciseResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseResource.class);

    private static final String ENTITY_NAME = "exercise";
        
    private final ExerciseService exerciseService;

    public ExerciseResource(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /**
     * POST  /exercises : Create a new exercise.
     *
     * @param exerciseDTO the exerciseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseDTO, or with status 400 (Bad Request) if the exercise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercises")
    @Timed
    public ResponseEntity<ExerciseDTO> createExercise(@Valid @RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to save Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new exercise cannot already have an ID")).body(null);
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.created(new URI("/api/exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercises : Updates an existing exercise.
     *
     * @param exerciseDTO the exerciseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseDTO,
     * or with status 400 (Bad Request) if the exerciseDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercises")
    @Timed
    public ResponseEntity<ExerciseDTO> updateExercise(@Valid @RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to update Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() == null) {
            return createExercise(exerciseDTO);
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exerciseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercises : get all the exercises.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of exercises in body
     */
    @GetMapping("/exercises")
    @Timed
    public List<ExerciseDTO> getAllExercises() {
        log.debug("REST request to get all Exercises");
        return exerciseService.findAll();
    }

    /**
     * GET  /exercises/:id : get the "id" exercise.
     *
     * @param id the id of the exerciseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercises/{id}")
    @Timed
    public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
        log.debug("REST request to get Exercise : {}", id);
        ExerciseDTO exerciseDTO = exerciseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exerciseDTO));
    }

    /**
     * DELETE  /exercises/:id : delete the "id" exercise.
     *
     * @param id the id of the exerciseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercises/{id}")
    @Timed
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        log.debug("REST request to delete Exercise : {}", id);
        exerciseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
