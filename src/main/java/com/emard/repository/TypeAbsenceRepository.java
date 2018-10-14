package com.emard.repository;

import com.emard.domain.TypeAbsence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;



/**
 * Spring Data JPA repository for the TypeAbsence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAbsenceRepository extends JpaRepository<TypeAbsence, Long> {

    Page<TypeAbsence> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
