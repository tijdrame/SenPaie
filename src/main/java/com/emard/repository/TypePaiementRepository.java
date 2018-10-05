package com.emard.repository;

import com.emard.domain.TypePaiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypePaiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePaiementRepository extends JpaRepository<TypePaiement, Long> {

    Page<TypePaiement> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
