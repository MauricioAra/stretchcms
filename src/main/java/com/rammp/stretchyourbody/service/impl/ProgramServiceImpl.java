package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.ProgramService;
import com.rammp.stretchyourbody.domain.Program;
import com.rammp.stretchyourbody.repository.ProgramRepository;
import com.rammp.stretchyourbody.service.dto.ProgramDTO;
import com.rammp.stretchyourbody.service.mapper.ProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Program.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService{

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    public ProgramServiceImpl(ProgramRepository programRepository, ProgramMapper programMapper) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
    }

    /**
     * Save a program.
     *
     * @param programDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProgramDTO save(ProgramDTO programDTO) {
        log.debug("Request to save Program : {}", programDTO);
        Program program = programMapper.programDTOToProgram(programDTO);
        program = programRepository.save(program);
        ProgramDTO result = programMapper.programToProgramDTO(program);
        return result;
    }

    /**
     *  Get all the programs.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProgramDTO> findAll() {
        log.debug("Request to get all Programs");
        List<ProgramDTO> result = programRepository.findAllWithEagerRelationships().stream()
            .map(programMapper::programToProgramDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one program by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProgramDTO findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        Program program = programRepository.findOneWithEagerRelationships(id);
        ProgramDTO programDTO = programMapper.programToProgramDTO(program);
        return programDTO;
    }

    /**
     *  Delete the  program by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.delete(id);
    }


    public List<ProgramDTO> findByUser(Long id){
        List<ProgramDTO> result = programRepository.findByUserAppId(id).stream()
            .map(programMapper::programToProgramDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
}
