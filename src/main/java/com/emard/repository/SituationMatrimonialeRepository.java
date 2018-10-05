package com.emard.repository;

import com.emard.domain.SituationMatrimoniale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SituationMatrimoniale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituationMatrimonialeRepository extends JpaRepository<SituationMatrimoniale, Long> {

    Page<SituationMatrimoniale> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
