package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.UserAppService;
import com.rammp.stretchyourbody.domain.UserApp;
import com.rammp.stretchyourbody.repository.UserAppRepository;
import com.rammp.stretchyourbody.service.dto.UserAppDTO;
import com.rammp.stretchyourbody.service.mapper.UserAppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserApp.
 */
@Service
@Transactional
public class UserAppServiceImpl implements UserAppService{

    private final Logger log = LoggerFactory.getLogger(UserAppServiceImpl.class);
    
    private final UserAppRepository userAppRepository;

    private final UserAppMapper userAppMapper;

    public UserAppServiceImpl(UserAppRepository userAppRepository, UserAppMapper userAppMapper) {
        this.userAppRepository = userAppRepository;
        this.userAppMapper = userAppMapper;
    }

    /**
     * Save a userApp.
     *
     * @param userAppDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAppDTO save(UserAppDTO userAppDTO) {
        log.debug("Request to save UserApp : {}", userAppDTO);
        UserApp userApp = userAppMapper.userAppDTOToUserApp(userAppDTO);
        userApp = userAppRepository.save(userApp);
        UserAppDTO result = userAppMapper.userAppToUserAppDTO(userApp);
        return result;
    }

    /**
     *  Get all the userApps.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserAppDTO> findAll() {
        log.debug("Request to get all UserApps");
        List<UserAppDTO> result = userAppRepository.findAllWithEagerRelationships().stream()
            .map(userAppMapper::userAppToUserAppDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one userApp by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserAppDTO findOne(Long id) {
        log.debug("Request to get UserApp : {}", id);
        UserApp userApp = userAppRepository.findOneWithEagerRelationships(id);
        UserAppDTO userAppDTO = userAppMapper.userAppToUserAppDTO(userApp);
        return userAppDTO;
    }

    /**
     *  Delete the  userApp by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserApp : {}", id);
        userAppRepository.delete(id);
    }
}
