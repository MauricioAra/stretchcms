package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.domain.Exercise;
import com.rammp.stretchyourbody.domain.Food;
import com.rammp.stretchyourbody.domain.Program;
import com.rammp.stretchyourbody.repository.ExerciseRepository;
import com.rammp.stretchyourbody.repository.FoodRepository;
import com.rammp.stretchyourbody.repository.ProgramRepository;
import com.rammp.stretchyourbody.service.dto.RecommendedDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by paulasegura on 11/7/17.
 */
@Service
@Transactional
public class RecommendedService {
    private final ProgramRepository programRepository;
    private final FoodRepository foodRepository;
    private final ExerciseRepository exerciseRepository;

    public RecommendedService(ProgramRepository programRepository, FoodRepository foodRepository, ExerciseRepository exerciseRepository) {
        this.programRepository = programRepository;
        this.foodRepository = foodRepository;
        this.exerciseRepository = exerciseRepository;
    }


    public List<RecommendedDTO> findByRecommended(){
        List<RecommendedDTO> recommendedDTOs = new ArrayList<>();

        List<RecommendedDTO> programsRecommended = buildPrograms();
        List<RecommendedDTO> exerciseRecommended = buildExercice();
        List<RecommendedDTO> foodRecommended = buildFood();
        recommendedDTOs = joinProgramExcersice(programsRecommended,exerciseRecommended,foodRecommended);

        return recommendedDTOs;
    }
    private List<RecommendedDTO> joinProgramExcersice(List<RecommendedDTO> programsRecommended,List<RecommendedDTO> exerciseRecommended,List<RecommendedDTO> foodRecommended){
        List<RecommendedDTO> mixRecommendations = new ArrayList<>(programsRecommended);
        mixRecommendations.addAll(exerciseRecommended);

        List<RecommendedDTO> allMixRecommendations = new ArrayList<>(mixRecommendations);
        allMixRecommendations.addAll(foodRecommended);

        return allMixRecommendations;
    }
    private List<RecommendedDTO> buildPrograms(){
        List<RecommendedDTO> recommendedDTOs = new ArrayList<>();
        List<Program> programs = programRepository.findByIsRecommended(true);

        for(int i = 0; i < programs.size();i++){
            RecommendedDTO recommendedDTO = new RecommendedDTO();
            recommendedDTO.setId(programs.get(i).getId());
            recommendedDTO.setName(programs.get(i).getName());
            recommendedDTO.setTag("p");
            recommendedDTOs.add(recommendedDTO);
        }

        return recommendedDTOs;
    }

    private List<RecommendedDTO> buildExercice(){
        List<RecommendedDTO> recommendedDTOs = new ArrayList<>();
        List<Exercise> exercises = exerciseRepository.findByIsRecommended(true);

        for(int i=0; i< exercises.size();i++){
            RecommendedDTO recommendedDTO = new RecommendedDTO();
            recommendedDTO.setId(exercises.get(i).getId());
            recommendedDTO.setName(exercises.get(i).getName());
            recommendedDTO.setTag("e");
            recommendedDTOs.add(recommendedDTO);
        }

        return recommendedDTOs;
    }

    private List<RecommendedDTO> buildFood(){
        List<RecommendedDTO> recommendedDTOs = new ArrayList<>();
        List<Food> foods = foodRepository.findByIsRecommended(true);

        for(int i=0; i< foods.size();i++){
            RecommendedDTO recommendedDTO = new RecommendedDTO();
            recommendedDTO.setId(foods.get(i).getId());
            recommendedDTO.setName(foods.get(i).getName());
            recommendedDTO.setTag("f");
            recommendedDTOs.add(recommendedDTO);
        }

        return recommendedDTOs;
    }
}
