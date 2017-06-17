package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.UserHealthService;
import com.rammp.stretchyourbody.domain.UserHealth;
import com.rammp.stretchyourbody.repository.UserHealthRepository;
import com.rammp.stretchyourbody.service.dto.UserHealthDTO;
import com.rammp.stretchyourbody.service.mapper.UserHealthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing UserHealth.
 */
@Service
@Transactional
public class UserHealthServiceImpl implements UserHealthService{

    private final Logger log = LoggerFactory.getLogger(UserHealthServiceImpl.class);
    
    private final UserHealthRepository userHealthRepository;

    private final UserHealthMapper userHealthMapper;

    public UserHealthServiceImpl(UserHealthRepository userHealthRepository, UserHealthMapper userHealthMapper) {
        this.userHealthRepository = userHealthRepository;
        this.userHealthMapper = userHealthMapper;
    }

    /**
     * Save a userHealth.
     *
     * @param userHealthDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserHealthDTO save(UserHealthDTO userHealthDTO) {
        log.debug("Request to save UserHealth : {}", userHealthDTO);
        UserHealth userHealth = userHealthMapper.userHealthDTOToUserHealth(userHealthDTO);
        userHealth = userHealthRepository.save(userHealth);
        UserHealthDTO result = userHealthMapper.userHealthToUserHealthDTO(userHealth);
        return result;
    }

    /**
     *  Get all the userHealths.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserHealthDTO> findAll() {
        log.debug("Request to get all UserHealths");
        List<UserHealthDTO> result = userHealthRepository.findAllWithEagerRelationships().stream()
            .map(userHealthMapper::userHealthToUserHealthDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }


    /**
     *  get all the userHealths where UserApp is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserHealthDTO> findAllWhereUserAppIsNull() {
        log.debug("Request to get all userHealths where UserApp is null");
        return StreamSupport
            .stream(userHealthRepository.findAll().spliterator(), false)
            .filter(userHealth -> userHealth.getUserApp() == null)
            .map(userHealthMapper::userHealthToUserHealthDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one userHealth by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserHealthDTO findOne(Long id) {
        log.debug("Request to get UserHealth : {}", id);
        UserHealth userHealth = userHealthRepository.findOneWithEagerRelationships(id);
        UserHealthDTO userHealthDTO = userHealthMapper.userHealthToUserHealthDTO(userHealth);
        return userHealthDTO;
    }

    /**
     *  Delete the  userHealth by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserHealth : {}", id);
        userHealthRepository.delete(id);
    }
}
