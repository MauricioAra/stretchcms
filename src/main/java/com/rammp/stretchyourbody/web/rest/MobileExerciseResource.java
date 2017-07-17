package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.ExerciseService;
import com.rammp.stretchyourbody.service.dto.ExerciseDTO;
import com.rammp.stretchyourbody.service.dto.RecommendedDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mbp on 7/10/17.
 */
@RestController
@RequestMapping("/api/app")
public class MobileExerciseResource {

    private final ExerciseService exerciseService;


    public MobileExerciseResource(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/exercise_by_body/{id}")
    @Timed
    public List<ExerciseDTO> getAllProgramsRecommended(@PathVariable Long id) {
        return exerciseService.findByBodyPart(id);
    }
}
