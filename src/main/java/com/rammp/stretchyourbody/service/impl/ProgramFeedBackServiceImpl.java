package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.ProgramFeedBackService;
import com.rammp.stretchyourbody.domain.ProgramFeedBack;
import com.rammp.stretchyourbody.repository.ProgramFeedBackRepository;
import com.rammp.stretchyourbody.service.dto.ProgramFeedBackDTO;
import com.rammp.stretchyourbody.service.mapper.ProgramFeedBackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProgramFeedBack.
 */
@Service
@Transactional
public class ProgramFeedBackServiceImpl implements ProgramFeedBackService{

    private final Logger log = LoggerFactory.getLogger(ProgramFeedBackServiceImpl.class);
    
    private final ProgramFeedBackRepository programFeedBackRepository;

    private final ProgramFeedBackMapper programFeedBackMapper;

    public ProgramFeedBackServiceImpl(ProgramFeedBackRepository programFeedBackRepository, ProgramFeedBackMapper programFeedBackMapper) {
        this.programFeedBackRepository = programFeedBackRepository;
        this.programFeedBackMapper = programFeedBackMapper;
    }

    /**
     * Save a programFeedBack.
     *
     * @param programFeedBackDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProgramFeedBackDTO save(ProgramFeedBackDTO programFeedBackDTO) {
        log.debug("Request to save ProgramFeedBack : {}", programFeedBackDTO);
        ProgramFeedBack programFeedBack = programFeedBackMapper.programFeedBackDTOToProgramFeedBack(programFeedBackDTO);
        programFeedBack = programFeedBackRepository.save(programFeedBack);
        ProgramFeedBackDTO result = programFeedBackMapper.programFeedBackToProgramFeedBackDTO(programFeedBack);
        return result;
    }

    /**
     *  Get all the programFeedBacks.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProgramFeedBackDTO> findAll() {
        log.debug("Request to get all ProgramFeedBacks");
        List<ProgramFeedBackDTO> result = programFeedBackRepository.findAll().stream()
            .map(programFeedBackMapper::programFeedBackToProgramFeedBackDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one programFeedBack by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProgramFeedBackDTO findOne(Long id) {
        log.debug("Request to get ProgramFeedBack : {}", id);
        ProgramFeedBack programFeedBack = programFeedBackRepository.findOne(id);
        ProgramFeedBackDTO programFeedBackDTO = programFeedBackMapper.programFeedBackToProgramFeedBackDTO(programFeedBack);
        return programFeedBackDTO;
    }

    /**
     *  Delete the  programFeedBack by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProgramFeedBack : {}", id);
        programFeedBackRepository.delete(id);
    }
}
