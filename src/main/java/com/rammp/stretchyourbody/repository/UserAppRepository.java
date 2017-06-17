package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.UserApp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserApp entity.
 */
@SuppressWarnings("unused")
public interface UserAppRepository extends JpaRepository<UserApp,Long> {

    @Query("select distinct userApp from UserApp userApp left join fetch userApp.exercises")
    List<UserApp> findAllWithEagerRelationships();

    @Query("select userApp from UserApp userApp left join fetch userApp.exercises where userApp.id =:id")
    UserApp findOneWithEagerRelationships(@Param("id") Long id);

}
