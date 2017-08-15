package com.rammp.stretchyourbody.repository;

import com.rammp.stretchyourbody.domain.BodyPointRegistry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BodyPointRegistry entity.
 */
@SuppressWarnings("unused")
public interface BodyPointRegistryRepository extends JpaRepository<BodyPointRegistry,Long> {

}
