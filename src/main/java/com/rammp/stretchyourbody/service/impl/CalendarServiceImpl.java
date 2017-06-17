package com.rammp.stretchyourbody.service.impl;

import com.rammp.stretchyourbody.service.CalendarService;
import com.rammp.stretchyourbody.domain.Calendar;
import com.rammp.stretchyourbody.repository.CalendarRepository;
import com.rammp.stretchyourbody.service.dto.CalendarDTO;
import com.rammp.stretchyourbody.service.mapper.CalendarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Calendar.
 */
@Service
@Transactional
public class CalendarServiceImpl implements CalendarService{

    private final Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);
    
    private final CalendarRepository calendarRepository;

    private final CalendarMapper calendarMapper;

    public CalendarServiceImpl(CalendarRepository calendarRepository, CalendarMapper calendarMapper) {
        this.calendarRepository = calendarRepository;
        this.calendarMapper = calendarMapper;
    }

    /**
     * Save a calendar.
     *
     * @param calendarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CalendarDTO save(CalendarDTO calendarDTO) {
        log.debug("Request to save Calendar : {}", calendarDTO);
        Calendar calendar = calendarMapper.calendarDTOToCalendar(calendarDTO);
        calendar = calendarRepository.save(calendar);
        CalendarDTO result = calendarMapper.calendarToCalendarDTO(calendar);
        return result;
    }

    /**
     *  Get all the calendars.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CalendarDTO> findAll() {
        log.debug("Request to get all Calendars");
        List<CalendarDTO> result = calendarRepository.findAllWithEagerRelationships().stream()
            .map(calendarMapper::calendarToCalendarDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one calendar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CalendarDTO findOne(Long id) {
        log.debug("Request to get Calendar : {}", id);
        Calendar calendar = calendarRepository.findOneWithEagerRelationships(id);
        CalendarDTO calendarDTO = calendarMapper.calendarToCalendarDTO(calendar);
        return calendarDTO;
    }

    /**
     *  Delete the  calendar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calendar : {}", id);
        calendarRepository.delete(id);
    }
}
