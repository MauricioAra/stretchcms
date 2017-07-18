package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.BodyPart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BodyPart entity.
 */
@SuppressWarnings("unused")
public interface BodyPartRepository extends JpaRepository<BodyPart,Long> {
    public List<BodyPart> findBySubCategoryId(Long id);
}
