package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.ExerciseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Exercise and its DTO ExerciseDTO.
 */
@Mapper(componentModel = "spring", uses = {BodyPartMapper.class, })
public interface ExerciseMapper {

    @Mapping(source = "bodyPart.id", target = "bodyPartId")
    @Mapping(source = "bodyPart.name", target = "bodyPartName")
    ExerciseDTO exerciseToExerciseDTO(Exercise exercise);

    List<ExerciseDTO> exercisesToExerciseDTOs(List<Exercise> exercises);

    @Mapping(source = "bodyPartId", target = "bodyPart")
    @Mapping(target = "userApps", ignore = true)
    @Mapping(target = "programs", ignore = true)
    Exercise exerciseDTOToExercise(ExerciseDTO exerciseDTO);

    List<Exercise> exerciseDTOsToExercises(List<ExerciseDTO> exerciseDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Exercise exerciseFromId(Long id) {
        if (id == null) {
            return null;
        }
        Exercise exercise = new Exercise();
        exercise.setId(id);
        return exercise;
    }
    

}
