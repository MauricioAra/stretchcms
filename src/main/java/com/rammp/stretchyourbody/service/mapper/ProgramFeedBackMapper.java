package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.ProgramFeedBackDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProgramFeedBack and its DTO ProgramFeedBackDTO.
 */
@Mapper(componentModel = "spring", uses = {ProgramMapper.class, })
public interface ProgramFeedBackMapper {

    @Mapping(source = "program.id", target = "programId")
    @Mapping(source = "program.name", target = "programName")
    ProgramFeedBackDTO programFeedBackToProgramFeedBackDTO(ProgramFeedBack programFeedBack);

    List<ProgramFeedBackDTO> programFeedBacksToProgramFeedBackDTOs(List<ProgramFeedBack> programFeedBacks);

    @Mapping(source = "programId", target = "program")
    ProgramFeedBack programFeedBackDTOToProgramFeedBack(ProgramFeedBackDTO programFeedBackDTO);

    List<ProgramFeedBack> programFeedBackDTOsToProgramFeedBacks(List<ProgramFeedBackDTO> programFeedBackDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default ProgramFeedBack programFeedBackFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProgramFeedBack programFeedBack = new ProgramFeedBack();
        programFeedBack.setId(id);
        return programFeedBack;
    }
    

}
