package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.Calendar;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Calendar entity.
 */
@SuppressWarnings("unused")
public interface CalendarRepository extends JpaRepository<Calendar,Long> {

    @Query("select distinct calendar from Calendar calendar left join fetch calendar.programs")
    List<Calendar> findAllWithEagerRelationships();

    @Query("select calendar from Calendar calendar left join fetch calendar.programs where calendar.id =:id")
    Calendar findOneWithEagerRelationships(@Param("id") Long id);

}
