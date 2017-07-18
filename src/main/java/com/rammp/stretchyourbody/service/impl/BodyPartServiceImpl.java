package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.BodyPartService;
import com.rammp.stretchyourbody.domain.BodyPart;
import com.rammp.stretchyourbody.repository.BodyPartRepository;
import com.rammp.stretchyourbody.service.dto.BodyPartDTO;
import com.rammp.stretchyourbody.service.mapper.BodyPartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BodyPart.
 */
@Service
@Transactional
public class BodyPartServiceImpl implements BodyPartService{

    private final Logger log = LoggerFactory.getLogger(BodyPartServiceImpl.class);

    private final BodyPartRepository bodyPartRepository;

    private final BodyPartMapper bodyPartMapper;

    public BodyPartServiceImpl(BodyPartRepository bodyPartRepository, BodyPartMapper bodyPartMapper) {
        this.bodyPartRepository = bodyPartRepository;
        this.bodyPartMapper = bodyPartMapper;
    }

    /**
     * Save a bodyPart.
     *
     * @param bodyPartDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BodyPartDTO save(BodyPartDTO bodyPartDTO) {
        log.debug("Request to save BodyPart : {}", bodyPartDTO);
        BodyPart bodyPart = bodyPartMapper.bodyPartDTOToBodyPart(bodyPartDTO);
        bodyPart = bodyPartRepository.save(bodyPart);
        BodyPartDTO result = bodyPartMapper.bodyPartToBodyPartDTO(bodyPart);
        return result;
    }

    /**
     *  Get all the bodyParts.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BodyPartDTO> findAll() {
        log.debug("Request to get all BodyParts");
        List<BodyPartDTO> result = bodyPartRepository.findAll().stream()
            .map(bodyPartMapper::bodyPartToBodyPartDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one bodyPart by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BodyPartDTO findOne(Long id) {
        log.debug("Request to get BodyPart : {}", id);
        BodyPart bodyPart = bodyPartRepository.findOne(id);
        BodyPartDTO bodyPartDTO = bodyPartMapper.bodyPartToBodyPartDTO(bodyPart);
        return bodyPartDTO;
    }

    /**
     *  Delete the  bodyPart by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BodyPart : {}", id);
        bodyPartRepository.delete(id);
    }


    public List<BodyPartDTO> findBySubcategory(Long id){
        List<BodyPartDTO> result = bodyPartRepository.findBySubCategoryId(id).stream()
            .map(bodyPartMapper::bodyPartToBodyPartDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

}
