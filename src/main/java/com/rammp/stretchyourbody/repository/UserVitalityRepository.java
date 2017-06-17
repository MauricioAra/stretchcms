package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.UserVitality;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserVitality entity.
 */
@SuppressWarnings("unused")
public interface UserVitalityRepository extends JpaRepository<UserVitality,Long> {

}
