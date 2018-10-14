package com.emard.repository;

import com.emard.domain.TypePaiement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypePaiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePaiementRepository extends JpaRepository<TypePaiement, Long> {

}
