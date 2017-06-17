package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.ProgramDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Program and its DTO ProgramDTO.
 */
@Mapper(componentModel = "spring", uses = {ExerciseMapper.class, UserAppMapper.class, })
public interface ProgramMapper {

    @Mapping(source = "userApp.id", target = "userAppId")
    ProgramDTO programToProgramDTO(Program program);

    List<ProgramDTO> programsToProgramDTOs(List<Program> programs);

    @Mapping(target = "programFeedBacks", ignore = true)
    @Mapping(source = "userAppId", target = "userApp")
    @Mapping(target = "calendars", ignore = true)
    Program programDTOToProgram(ProgramDTO programDTO);

    List<Program> programDTOsToPrograms(List<ProgramDTO> programDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Program programFromId(Long id) {
        if (id == null) {
            return null;
        }
        Program program = new Program();
        program.setId(id);
        return program;
    }
    

}
