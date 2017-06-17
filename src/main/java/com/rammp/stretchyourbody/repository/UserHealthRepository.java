package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.UserHealth;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserHealth entity.
 */
@SuppressWarnings("unused")
public interface UserHealthRepository extends JpaRepository<UserHealth,Long> {

    @Query("select distinct userHealth from UserHealth userHealth left join fetch userHealth.bodyParts")
    List<UserHealth> findAllWithEagerRelationships();

    @Query("select userHealth from UserHealth userHealth left join fetch userHealth.bodyParts where userHealth.id =:id")
    UserHealth findOneWithEagerRelationships(@Param("id") Long id);

}
