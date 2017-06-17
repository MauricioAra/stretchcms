package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.FoodTagDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FoodTag and its DTO FoodTagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoodTagMapper {

    FoodTagDTO foodTagToFoodTagDTO(FoodTag foodTag);

    List<FoodTagDTO> foodTagsToFoodTagDTOs(List<FoodTag> foodTags);

    @Mapping(target = "foods", ignore = true)
    FoodTag foodTagDTOToFoodTag(FoodTagDTO foodTagDTO);

    List<FoodTag> foodTagDTOsToFoodTags(List<FoodTagDTO> foodTagDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default FoodTag foodTagFromId(Long id) {
        if (id == null) {
            return null;
        }
        FoodTag foodTag = new FoodTag();
        foodTag.setId(id);
        return foodTag;
    }
    

}
