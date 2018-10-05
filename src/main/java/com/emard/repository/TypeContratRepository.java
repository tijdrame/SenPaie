package com.emard.repository;

import com.emard.domain.TypeContrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeContratRepository extends JpaRepository<TypeContrat, Long> {

    Page<TypeContrat> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
