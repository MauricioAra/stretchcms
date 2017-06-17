package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.SubCategoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SubCategory and its DTO SubCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, })
public interface SubCategoryMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    SubCategoryDTO subCategoryToSubCategoryDTO(SubCategory subCategory);

    List<SubCategoryDTO> subCategoriesToSubCategoryDTOs(List<SubCategory> subCategories);

    @Mapping(target = "bodyParts", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    SubCategory subCategoryDTOToSubCategory(SubCategoryDTO subCategoryDTO);

    List<SubCategory> subCategoryDTOsToSubCategories(List<SubCategoryDTO> subCategoryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default SubCategory subCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);
        return subCategory;
    }
    

}
