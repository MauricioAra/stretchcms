package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.UserVitalityDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserVitality and its DTO UserVitalityDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAppMapper.class, })
public interface UserVitalityMapper {

    @Mapping(source = "userApp.id", target = "userAppId")
    @Mapping(source = "userApp.name", target = "userAppName")
    UserVitalityDTO userVitalityToUserVitalityDTO(UserVitality userVitality);

    List<UserVitalityDTO> userVitalitiesToUserVitalityDTOs(List<UserVitality> userVitalities);

    @Mapping(source = "userAppId", target = "userApp")
    UserVitality userVitalityDTOToUserVitality(UserVitalityDTO userVitalityDTO);

    List<UserVitality> userVitalityDTOsToUserVitalities(List<UserVitalityDTO> userVitalityDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default UserVitality userVitalityFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserVitality userVitality = new UserVitality();
        userVitality.setId(id);
        return userVitality;
    }
    

}
