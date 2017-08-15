package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.BodyPointRegistryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BodyPointRegistry and its DTO BodyPointRegistryDTO.
 */
@Mapper(componentModel = "spring", uses = {BodyPointMapper.class, })
public interface BodyPointRegistryMapper {

    @Mapping(source = "bodyPoint.id", target = "bodyPointId")
    @Mapping(source = "bodyPoint.bodypart", target = "bodyPointBodypart")
    BodyPointRegistryDTO bodyPointRegistryToBodyPointRegistryDTO(BodyPointRegistry bodyPointRegistry);

    List<BodyPointRegistryDTO> bodyPointRegistriesToBodyPointRegistryDTOs(List<BodyPointRegistry> bodyPointRegistries);

    @Mapping(source = "bodyPointId", target = "bodyPoint")
    BodyPointRegistry bodyPointRegistryDTOToBodyPointRegistry(BodyPointRegistryDTO bodyPointRegistryDTO);

    List<BodyPointRegistry> bodyPointRegistryDTOsToBodyPointRegistries(List<BodyPointRegistryDTO> bodyPointRegistryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default BodyPointRegistry bodyPointRegistryFromId(Long id) {
        if (id == null) {
            return null;
        }
        BodyPointRegistry bodyPointRegistry = new BodyPointRegistry();
        bodyPointRegistry.setId(id);
        return bodyPointRegistry;
    }
    

}
