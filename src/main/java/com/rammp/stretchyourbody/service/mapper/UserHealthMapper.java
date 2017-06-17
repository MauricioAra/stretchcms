package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.UserHealthDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserHealth and its DTO UserHealthDTO.
 */
@Mapper(componentModel = "spring", uses = {BodyPartMapper.class, })
public interface UserHealthMapper {

    UserHealthDTO userHealthToUserHealthDTO(UserHealth userHealth);

    List<UserHealthDTO> userHealthsToUserHealthDTOs(List<UserHealth> userHealths);

    @Mapping(target = "userApp", ignore = true)
    UserHealth userHealthDTOToUserHealth(UserHealthDTO userHealthDTO);

    List<UserHealth> userHealthDTOsToUserHealths(List<UserHealthDTO> userHealthDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default UserHealth userHealthFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserHealth userHealth = new UserHealth();
        userHealth.setId(id);
        return userHealth;
    }
    

}
