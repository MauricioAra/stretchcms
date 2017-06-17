package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.ProgramFeedBack;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProgramFeedBack entity.
 */
@SuppressWarnings("unused")
public interface ProgramFeedBackRepository extends JpaRepository<ProgramFeedBack,Long> {

}
