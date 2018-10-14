package com.emard.repository;

import com.emard.domain.TypeContrat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeContratRepository extends JpaRepository<TypeContrat, Long> {

}
