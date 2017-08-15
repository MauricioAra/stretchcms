package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.UserVitalityService;
import com.rammp.stretchyourbody.domain.UserVitality;
import com.rammp.stretchyourbody.repository.UserVitalityRepository;
import com.rammp.stretchyourbody.service.dto.ResultAverageDTO;
import com.rammp.stretchyourbody.service.dto.UserVitalityDTO;
import com.rammp.stretchyourbody.service.mapper.UserVitalityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserVitality.
 */
@Service
@Transactional
public class UserVitalityServiceImpl implements UserVitalityService{

    private final Logger log = LoggerFactory.getLogger(UserVitalityServiceImpl.class);

    private final UserVitalityRepository userVitalityRepository;

    private final UserVitalityMapper userVitalityMapper;

    public UserVitalityServiceImpl(UserVitalityRepository userVitalityRepository, UserVitalityMapper userVitalityMapper) {
        this.userVitalityRepository = userVitalityRepository;
        this.userVitalityMapper = userVitalityMapper;
    }

    /**
     * Save a userVitality.
     *
     * @param userVitalityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserVitalityDTO save(UserVitalityDTO userVitalityDTO) {
        log.debug("Request to save UserVitality : {}", userVitalityDTO);
        UserVitality userVitality = userVitalityMapper.userVitalityDTOToUserVitality(userVitalityDTO);
        userVitality = userVitalityRepository.save(userVitality);
        UserVitalityDTO result = userVitalityMapper.userVitalityToUserVitalityDTO(userVitality);
        return result;
    }

    /**
     *  Get all the userVitalities.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserVitalityDTO> findAll() {
        log.debug("Request to get all UserVitalities");
        List<UserVitalityDTO> result = userVitalityRepository.findAll().stream()
            .map(userVitalityMapper::userVitalityToUserVitalityDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one userVitality by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserVitalityDTO findOne(Long id) {
        log.debug("Request to get UserVitality : {}", id);
        UserVitality userVitality = userVitalityRepository.findOne(id);
        UserVitalityDTO userVitalityDTO = userVitalityMapper.userVitalityToUserVitalityDTO(userVitality);
        return userVitalityDTO;
    }

    /**
     *  Delete the  userVitality by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserVitality : {}", id);
        userVitalityRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultAverageDTO getAverage(Long id) {

        List<UserVitality> userVitalities =  userVitalityRepository.findByUserAppId(id);
        ResultAverageDTO resultAverageDTO = new ResultAverageDTO();
        int promedio = 0;

        if(userVitalities.size() == 0){
            resultAverageDTO.setAverage(promedio);
        }else{
            for(int i=0; i<userVitalities.size(); i++){
                promedio = promedio + userVitalities.get(i).getRange().intValue();
            }
            promedio = promedio/userVitalities.size();
            resultAverageDTO.setAverage(promedio);
        }

        return resultAverageDTO;
    }
}
