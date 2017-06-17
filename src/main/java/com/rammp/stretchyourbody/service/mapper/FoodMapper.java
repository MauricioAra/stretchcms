package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.FoodDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Food and its DTO FoodDTO.
 */
@Mapper(componentModel = "spring", uses = {FoodTagMapper.class, })
public interface FoodMapper {

    FoodDTO foodToFoodDTO(Food food);

    List<FoodDTO> foodsToFoodDTOs(List<Food> foods);

    Food foodDTOToFood(FoodDTO foodDTO);

    List<Food> foodDTOsToFoods(List<FoodDTO> foodDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Food foodFromId(Long id) {
        if (id == null) {
            return null;
        }
        Food food = new Food();
        food.setId(id);
        return food;
    }
    

}
