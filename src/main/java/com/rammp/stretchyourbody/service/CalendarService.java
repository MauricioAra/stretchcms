package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.service.dto.CalendarDTO;
import java.util.List;

/**
 * Service Interface for managing Calendar.
 */
public interface CalendarService {

    /**
     * Save a calendar.
     *
     * @param calendarDTO the entity to save
     * @return the persisted entity
     */
    CalendarDTO save(CalendarDTO calendarDTO);

    /**
     *  Get all the calendars.
     *  
     *  @return the list of entities
     */
    List<CalendarDTO> findAll();

    /**
     *  Get the "id" calendar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CalendarDTO findOne(Long id);

    /**
     *  Delete the "id" calendar.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
