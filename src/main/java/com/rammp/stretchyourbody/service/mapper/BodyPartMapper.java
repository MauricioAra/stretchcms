package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.BodyPartDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BodyPart and its DTO BodyPartDTO.
 */
@Mapper(componentModel = "spring", uses = {SubCategoryMapper.class, })
public interface BodyPartMapper {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    @Mapping(source = "subCategory.name", target = "subCategoryName")
    BodyPartDTO bodyPartToBodyPartDTO(BodyPart bodyPart);

    List<BodyPartDTO> bodyPartsToBodyPartDTOs(List<BodyPart> bodyParts);

    @Mapping(target = "exercises", ignore = true)
    @Mapping(source = "subCategoryId", target = "subCategory")
    @Mapping(target = "userHealths", ignore = true)
    BodyPart bodyPartDTOToBodyPart(BodyPartDTO bodyPartDTO);

    List<BodyPart> bodyPartDTOsToBodyParts(List<BodyPartDTO> bodyPartDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default BodyPart bodyPartFromId(Long id) {
        if (id == null) {
            return null;
        }
        BodyPart bodyPart = new BodyPart();
        bodyPart.setId(id);
        return bodyPart;
    }
    

}
