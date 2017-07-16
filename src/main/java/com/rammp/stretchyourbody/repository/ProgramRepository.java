package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.Program;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Program entity.
 */
@SuppressWarnings("unused")
public interface ProgramRepository extends JpaRepository<Program,Long> {

    public List<Program> findByUserAppId(Long id);

    @Query("select distinct program from Program program left join fetch program.exercises")
    List<Program> findAllWithEagerRelationships();

    @Query("select program from Program program left join fetch program.exercises where program.id =:id")
    Program findOneWithEagerRelationships(@Param("id") Long id);

    public List<Program> findByIsRecommended(boolean isRecom);

}
