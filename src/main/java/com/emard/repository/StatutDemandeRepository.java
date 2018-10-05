package com.emard.repository;

import com.emard.domain.StatutDemande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StatutDemande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatutDemandeRepository extends JpaRepository<StatutDemande, Long> {

    Page<StatutDemande> findByDeletedFalseOrderByLibelle(Pageable pageable);

    StatutDemande findByCodeAndAndDeletedFalse(String code);
}
