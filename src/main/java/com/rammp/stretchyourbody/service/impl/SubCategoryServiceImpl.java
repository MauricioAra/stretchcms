package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.SubCategoryService;
import com.rammp.stretchyourbody.domain.SubCategory;
import com.rammp.stretchyourbody.repository.SubCategoryRepository;
import com.rammp.stretchyourbody.service.dto.SubCategoryDTO;
import com.rammp.stretchyourbody.service.mapper.SubCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SubCategory.
 */
@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService{

    private final Logger log = LoggerFactory.getLogger(SubCategoryServiceImpl.class);

    private final SubCategoryRepository subCategoryRepository;

    private final SubCategoryMapper subCategoryMapper;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryMapper subCategoryMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryMapper = subCategoryMapper;
    }

    /**
     * Save a subCategory.
     *
     * @param subCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SubCategoryDTO save(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to save SubCategory : {}", subCategoryDTO);
        SubCategory subCategory = subCategoryMapper.subCategoryDTOToSubCategory(subCategoryDTO);
        subCategory = subCategoryRepository.save(subCategory);
        SubCategoryDTO result = subCategoryMapper.subCategoryToSubCategoryDTO(subCategory);
        return result;
    }

    /**
     *  Get all the subCategories.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryDTO> findAll() {
        log.debug("Request to get all SubCategories");
        List<SubCategoryDTO> result = subCategoryRepository.findAll().stream()
            .map(subCategoryMapper::subCategoryToSubCategoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one subCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SubCategoryDTO findOne(Long id) {
        log.debug("Request to get SubCategory : {}", id);
        SubCategory subCategory = subCategoryRepository.findOne(id);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.subCategoryToSubCategoryDTO(subCategory);
        return subCategoryDTO;
    }

    /**
     *  Delete the  subCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubCategory : {}", id);
        subCategoryRepository.delete(id);
    }

    public List<SubCategoryDTO> subCategoryByCategory(Long id){
        List<SubCategoryDTO> subCategoryDTOS = subCategoryRepository.findByCategoryIdAndStatus(id,true).stream()
            .map(subCategoryMapper::subCategoryToSubCategoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return subCategoryDTOS;
    }
}
