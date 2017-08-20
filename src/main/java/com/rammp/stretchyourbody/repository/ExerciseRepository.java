package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.Exercise;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Exercise entity.
 */
@SuppressWarnings("unused")
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
    public List<Exercise> findByIsRecommended(boolean isRecom);
    public List<Exercise> findByBodyPartId(Long id);

}
