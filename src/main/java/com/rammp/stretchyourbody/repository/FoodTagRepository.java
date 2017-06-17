package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.FoodTag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FoodTag entity.
 */
@SuppressWarnings("unused")
public interface FoodTagRepository extends JpaRepository<FoodTag,Long> {

}
