package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.FoodTagService;
import com.rammp.stretchyourbody.domain.FoodTag;
import com.rammp.stretchyourbody.repository.FoodTagRepository;
import com.rammp.stretchyourbody.service.dto.FoodTagDTO;
import com.rammp.stretchyourbody.service.mapper.FoodTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FoodTag.
 */
@Service
@Transactional
public class FoodTagServiceImpl implements FoodTagService{

    private final Logger log = LoggerFactory.getLogger(FoodTagServiceImpl.class);
    
    private final FoodTagRepository foodTagRepository;

    private final FoodTagMapper foodTagMapper;

    public FoodTagServiceImpl(FoodTagRepository foodTagRepository, FoodTagMapper foodTagMapper) {
        this.foodTagRepository = foodTagRepository;
        this.foodTagMapper = foodTagMapper;
    }

    /**
     * Save a foodTag.
     *
     * @param foodTagDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FoodTagDTO save(FoodTagDTO foodTagDTO) {
        log.debug("Request to save FoodTag : {}", foodTagDTO);
        FoodTag foodTag = foodTagMapper.foodTagDTOToFoodTag(foodTagDTO);
        foodTag = foodTagRepository.save(foodTag);
        FoodTagDTO result = foodTagMapper.foodTagToFoodTagDTO(foodTag);
        return result;
    }

    /**
     *  Get all the foodTags.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FoodTagDTO> findAll() {
        log.debug("Request to get all FoodTags");
        List<FoodTagDTO> result = foodTagRepository.findAll().stream()
            .map(foodTagMapper::foodTagToFoodTagDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one foodTag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FoodTagDTO findOne(Long id) {
        log.debug("Request to get FoodTag : {}", id);
        FoodTag foodTag = foodTagRepository.findOne(id);
        FoodTagDTO foodTagDTO = foodTagMapper.foodTagToFoodTagDTO(foodTag);
        return foodTagDTO;
    }

    /**
     *  Delete the  foodTag by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoodTag : {}", id);
        foodTagRepository.delete(id);
    }
}
