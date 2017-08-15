package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.BodyPoint;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BodyPoint entity.
 */
@SuppressWarnings("unused")
public interface BodyPointRepository extends JpaRepository<BodyPoint,Long> {

}
