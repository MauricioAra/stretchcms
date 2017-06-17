package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.UserAppDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserApp and its DTO UserAppDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserHealthMapper.class, ExerciseMapper.class, })
public interface UserAppMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "userHealth.id", target = "userHealthId")
    UserAppDTO userAppToUserAppDTO(UserApp userApp);

    List<UserAppDTO> userAppsToUserAppDTOs(List<UserApp> userApps);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "userHealthId", target = "userHealth")
    @Mapping(target = "programs", ignore = true)
    @Mapping(target = "userVitalities", ignore = true)
    UserApp userAppDTOToUserApp(UserAppDTO userAppDTO);

    List<UserApp> userAppDTOsToUserApps(List<UserAppDTO> userAppDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default UserApp userAppFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserApp userApp = new UserApp();
        userApp.setId(id);
        return userApp;
    }
    

}
