package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.BodyPointRegistryService;
import com.rammp.stretchyourbody.domain.BodyPointRegistry;
import com.rammp.stretchyourbody.repository.BodyPointRegistryRepository;
import com.rammp.stretchyourbody.service.dto.BodyPointRegistryDTO;
import com.rammp.stretchyourbody.service.mapper.BodyPointRegistryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BodyPointRegistry.
 */
@Service
@Transactional
public class BodyPointRegistryServiceImpl implements BodyPointRegistryService{

    private final Logger log = LoggerFactory.getLogger(BodyPointRegistryServiceImpl.class);
    
    private final BodyPointRegistryRepository bodyPointRegistryRepository;

    private final BodyPointRegistryMapper bodyPointRegistryMapper;

    public BodyPointRegistryServiceImpl(BodyPointRegistryRepository bodyPointRegistryRepository, BodyPointRegistryMapper bodyPointRegistryMapper) {
        this.bodyPointRegistryRepository = bodyPointRegistryRepository;
        this.bodyPointRegistryMapper = bodyPointRegistryMapper;
    }

    /**
     * Save a bodyPointRegistry.
     *
     * @param bodyPointRegistryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BodyPointRegistryDTO save(BodyPointRegistryDTO bodyPointRegistryDTO) {
        log.debug("Request to save BodyPointRegistry : {}", bodyPointRegistryDTO);
        BodyPointRegistry bodyPointRegistry = bodyPointRegistryMapper.bodyPointRegistryDTOToBodyPointRegistry(bodyPointRegistryDTO);
        bodyPointRegistry = bodyPointRegistryRepository.save(bodyPointRegistry);
        BodyPointRegistryDTO result = bodyPointRegistryMapper.bodyPointRegistryToBodyPointRegistryDTO(bodyPointRegistry);
        return result;
    }

    /**
     *  Get all the bodyPointRegistries.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BodyPointRegistryDTO> findAll() {
        log.debug("Request to get all BodyPointRegistries");
        List<BodyPointRegistryDTO> result = bodyPointRegistryRepository.findAll().stream()
            .map(bodyPointRegistryMapper::bodyPointRegistryToBodyPointRegistryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one bodyPointRegistry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BodyPointRegistryDTO findOne(Long id) {
        log.debug("Request to get BodyPointRegistry : {}", id);
        BodyPointRegistry bodyPointRegistry = bodyPointRegistryRepository.findOne(id);
        BodyPointRegistryDTO bodyPointRegistryDTO = bodyPointRegistryMapper.bodyPointRegistryToBodyPointRegistryDTO(bodyPointRegistry);
        return bodyPointRegistryDTO;
    }

    /**
     *  Delete the  bodyPointRegistry by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BodyPointRegistry : {}", id);
        bodyPointRegistryRepository.delete(id);
    }
}
