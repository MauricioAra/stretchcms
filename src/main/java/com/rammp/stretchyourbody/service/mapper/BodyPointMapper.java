package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.BodyPointDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BodyPoint and its DTO BodyPointDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAppMapper.class, })
public interface BodyPointMapper {

    @Mapping(source = "userApp.id", target = "userAppId")
    @Mapping(source = "userApp.name", target = "userAppName")
    BodyPointDTO bodyPointToBodyPointDTO(BodyPoint bodyPoint);

    List<BodyPointDTO> bodyPointsToBodyPointDTOs(List<BodyPoint> bodyPoints);

    @Mapping(source = "userAppId", target = "userApp")
    @Mapping(target = "bodyPointRegistries", ignore = true)
    BodyPoint bodyPointDTOToBodyPoint(BodyPointDTO bodyPointDTO);

    List<BodyPoint> bodyPointDTOsToBodyPoints(List<BodyPointDTO> bodyPointDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default BodyPoint bodyPointFromId(Long id) {
        if (id == null) {
            return null;
        }
        BodyPoint bodyPoint = new BodyPoint();
        bodyPoint.setId(id);
        return bodyPoint;
    }
    

}
