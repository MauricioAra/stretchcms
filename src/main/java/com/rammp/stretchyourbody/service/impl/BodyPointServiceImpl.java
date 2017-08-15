package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.BodyPointService;
import com.rammp.stretchyourbody.domain.BodyPoint;
import com.rammp.stretchyourbody.repository.BodyPointRepository;
import com.rammp.stretchyourbody.service.dto.BodyPointDTO;
import com.rammp.stretchyourbody.service.mapper.BodyPointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BodyPoint.
 */
@Service
@Transactional
public class BodyPointServiceImpl implements BodyPointService{

    private final Logger log = LoggerFactory.getLogger(BodyPointServiceImpl.class);
    
    private final BodyPointRepository bodyPointRepository;

    private final BodyPointMapper bodyPointMapper;

    public BodyPointServiceImpl(BodyPointRepository bodyPointRepository, BodyPointMapper bodyPointMapper) {
        this.bodyPointRepository = bodyPointRepository;
        this.bodyPointMapper = bodyPointMapper;
    }

    /**
     * Save a bodyPoint.
     *
     * @param bodyPointDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BodyPointDTO save(BodyPointDTO bodyPointDTO) {
        log.debug("Request to save BodyPoint : {}", bodyPointDTO);
        BodyPoint bodyPoint = bodyPointMapper.bodyPointDTOToBodyPoint(bodyPointDTO);
        bodyPoint = bodyPointRepository.save(bodyPoint);
        BodyPointDTO result = bodyPointMapper.bodyPointToBodyPointDTO(bodyPoint);
        return result;
    }

    /**
     *  Get all the bodyPoints.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BodyPointDTO> findAll() {
        log.debug("Request to get all BodyPoints");
        List<BodyPointDTO> result = bodyPointRepository.findAll().stream()
            .map(bodyPointMapper::bodyPointToBodyPointDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one bodyPoint by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BodyPointDTO findOne(Long id) {
        log.debug("Request to get BodyPoint : {}", id);
        BodyPoint bodyPoint = bodyPointRepository.findOne(id);
        BodyPointDTO bodyPointDTO = bodyPointMapper.bodyPointToBodyPointDTO(bodyPoint);
        return bodyPointDTO;
    }

    /**
     *  Delete the  bodyPoint by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BodyPoint : {}", id);
        bodyPointRepository.delete(id);
    }
}
