package com.rammp.stretchyourbody.service.mapper;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.service.dto.CalendarDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Calendar and its DTO CalendarDTO.
 */
@Mapper(componentModel = "spring", uses = {ProgramMapper.class, })
public interface CalendarMapper {

    CalendarDTO calendarToCalendarDTO(Calendar calendar);

    List<CalendarDTO> calendarsToCalendarDTOs(List<Calendar> calendars);

    Calendar calendarDTOToCalendar(CalendarDTO calendarDTO);

    List<Calendar> calendarDTOsToCalendars(List<CalendarDTO> calendarDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Calendar calendarFromId(Long id) {
        if (id == null) {
            return null;
        }
        Calendar calendar = new Calendar();
        calendar.setId(id);
        return calendar;
    }
    

}
